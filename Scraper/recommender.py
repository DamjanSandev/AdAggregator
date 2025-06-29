from db import SessionLocal
from sqlalchemy import text
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.preprocessing import OneHotEncoder, MinMaxScaler
from sklearn.compose import ColumnTransformer
import pandas as pd
import numpy as np
import requests
import os


BACKEND_PREF_ENDPOINT = "http://spring-backend:9090/api/preferences/bulk"
SCRAPER_SECRET = os.getenv("SCRAPER_SECRET")

headers = {
    "X-SCRAPER-KEY": SCRAPER_SECRET
}


def generate_recommendations():
    session = SessionLocal()
    try:
        ads_query = session.execute(text("SELECT * FROM ads"))
        ads = pd.DataFrame(ads_query.fetchall(), columns=ads_query.keys())
        if ads.empty:
            return {"status": "skipped", "message": "No ads found in DB"}

        inter_query = session.execute(
            text(
                "SELECT user_username, ad_id, strength FROM interactions WHERE interaction_type IN ('VIEW', 'CLICK', 'FAV')")
        )
        inter_df = pd.DataFrame(inter_query.fetchall(), columns=["user", "ad_id", "strength"])
        if inter_df.empty:
            return {"status": "skipped", "message": "No user interactions yet"}

        features = ["brand", "model", "fuel_type", "transmission", "body_type", "emission_type", "engine_power", "year",
                    "kilometers", "price"]
        transformer = ColumnTransformer([
            ("cat", OneHotEncoder(handle_unknown="ignore"), features[:6]),
            ("num", MinMaxScaler(), features[6:])
        ])

        X = transformer.fit_transform(ads[features])
        cos_sim = cosine_similarity(X)

        id_to_idx = {ad_id: idx for idx, ad_id in enumerate(ads["id"])}
        idx_to_id = {idx: ad_id for ad_id, idx in id_to_idx.items()}

        user_recommendations = {}

        for user, group in inter_df.groupby("user"):
            weighted_vectors = []
            total_weight = 0

            for _, row in group.iterrows():
                ad_id = row["ad_id"]
                weight = row["strength"]
                idx = id_to_idx.get(ad_id)
                if idx is not None:
                    weighted_vectors.append(cos_sim[idx] * weight)
                    total_weight += weight

            if not weighted_vectors or total_weight == 0:
                continue

            user_vector = np.sum(weighted_vectors, axis=0) / total_weight
            ranked = np.argsort(user_vector)[::-1]
            seen = {id_to_idx.get(ad_id) for ad_id in group["ad_id"] if ad_id in id_to_idx}
            rec_ads = [idx_to_id[i] for i in ranked if i not in seen][:10]
            user_recommendations[user] = rec_ads

        successes = 0
        failures = {}

        for user, ad_ids in user_recommendations.items():
            payload = {
                "user": user,
                "adIds": ad_ids
            }
            resp = requests.post(
                BACKEND_PREF_ENDPOINT,
                json=payload,
                headers=headers
            )
            if resp.ok:
                successes += 1
            else:
                failures[user] = resp.status_code

        return {
            "status": "ok",
            "sent": successes,
            "errors": failures
        }

    finally:
        session.close()
