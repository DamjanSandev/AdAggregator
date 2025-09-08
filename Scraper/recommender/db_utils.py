from db.db import get_session
from sqlalchemy import text
import pandas as pd

def fetch_ads():
    with get_session() as session:
        ads_query = session.execute(text("SELECT * FROM ads"))
        return pd.DataFrame(ads_query.fetchall(), columns=ads_query.keys())

def fetch_interactions():
    with get_session() as session:
        inter_query = session.execute(text(
            "SELECT user_username, ad_id, strength FROM interactions "
            "WHERE interaction_type IN ('VIEW','CLICK','FAV')"
        ))
        return pd.DataFrame(inter_query.fetchall(), columns=["user", "ad_id", "strength"])

