import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
import logging

from db_utils import fetch_ads, fetch_interactions
from features import build_feature_matrix
from publisher import publish_recommendations
from config import TOP_N_RECS


logger = logging.getLogger(__name__)


def generate_recommendations(top_n: int = TOP_N_RECS) -> dict:
    """
    Generate recommendations for each user based on cosine similarity
    of ad features weighted by user interactions.

    Args:
        top_n (int): number of recommendations per user

    Returns:
        dict: summary {status, sent, errors}
    """
    # 1. Load data
    ads = fetch_ads()
    if ads.empty:
        logger.warning("No ads found in DB.")
        return {"status": "skipped", "message": "No ads in DB"}

    interactions = fetch_interactions()
    if interactions.empty:
        logger.warning("No interactions found in DB.")
        return {"status": "skipped", "message": "No interactions in DB"}

    # 2. Build features
    X, id_to_idx, idx_to_id = build_feature_matrix(ads)
    cos_sim = cosine_similarity(X)

    # 3. Recommendations
    user_recommendations = {}

    for user, group in interactions.groupby("user"):
        weighted_vectors = []
        total_weight = 0

        for _, row in group.iterrows():
            ad_id, weight = row["ad_id"], row["strength"]
            idx = id_to_idx.get(ad_id)
            if idx is None:
                continue
            weighted_vectors.append(cos_sim[idx] * weight)
            total_weight += weight

        if not weighted_vectors or total_weight == 0:
            logger.debug("Skipping user=%s (no valid ads)", user)
            continue

        user_vector = np.sum(weighted_vectors, axis=0) / total_weight
        ranked = np.argsort(user_vector)[::-1]

        seen = {id_to_idx[ad_id] for ad_id in group["ad_id"] if ad_id in id_to_idx}
        rec_ads = [idx_to_id[i] for i in ranked if i not in seen][:top_n]

        user_recommendations[user] = rec_ads

    # 4. Publish
    return publish_recommendations(user_recommendations)
