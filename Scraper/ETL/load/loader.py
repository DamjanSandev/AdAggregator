import os
import requests
from dotenv import load_dotenv
from typing import List, Dict
from ETL.utils.logger import init_logger

load_dotenv()

SPRING_BACKEND_URL = os.getenv("SPRING_BACKEND_URL")
SCRAPER_SECRET = os.getenv("SCRAPER_SECRET")
HEADERS = {
    "Content-Type": "application/json",
    "X-SCRAPER-KEY": SCRAPER_SECRET
}

logger = init_logger(__name__)


def post_single_ad(ad: Dict) -> bool:
    try:
        response = requests.post(SPRING_BACKEND_URL, json=ad, headers=HEADERS, timeout=10)
        if response.status_code == 200:
            logger.info(f"Successfully posted ad: {ad.get('url')}")
            return True
        else:
            logger.warning(f"Failed to post ad: {ad.get('url')}")
            return False
    except requests.RequestException as exception:
        logger.error(f"Successfully posted ad: {ad.get('url')}")
        return False


def load_ads(ads: List[Dict]) -> dict:
    """
    Sends a batch of ads to the Spring backend.
    Returns summary with counts of successes/failures.
    """
    successes = 0
    failures = 0

    for ad in ads:
        if post_single_ad(ad):
            successes += 1
        else:
            failures += 1

    summary = {
        "total_ads": len(ads),
        "successes": successes,
        "failures": failures
    }

    logger.info(f"Load summary:{summary}")
    return summary
