# recommender/publisher.py
import logging
import requests
from config import BACKEND_PREF_ENDPOINT, HEADERS

logger = logging.getLogger(__name__)


def publish_recommendations(user_recommendations: dict) -> dict:
    """
    Publish user recommendations to backend.

    Args:
        user_recommendations (dict): {username: [ad_id1, ad_id2, ...]}

    Returns:
        dict: summary {status, sent, errors}
    """
    successes = 0
    failures = {}

    for user, ad_ids in user_recommendations.items():
        payload = {"user": user, "adIds": ad_ids}

        try:
            resp = requests.post(
                BACKEND_PREF_ENDPOINT,
                json=payload,
                headers=HEADERS,
                timeout=10,
            )

            if resp.ok:
                successes += 1
                logger.info("Published recommendations for user=%s (%d ads)", user, len(ad_ids))
            else:
                failures[user] = resp.status_code
                logger.error("Failed publishing for user=%s, status=%s", user, resp.status_code)

        except requests.RequestException as e:
            failures[user] = str(e)
            logger.exception("Exception while publishing for user=%s", user)

    return {"status": "ok", "sent": successes, "errors": failures}
