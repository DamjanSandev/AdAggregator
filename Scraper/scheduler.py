import requests
from sqlalchemy import text
from db import SessionLocal
from scraper import fetch_and_extract_features, get_ad_urls
import logging

logger = logging.getLogger("scheduler")
logging.basicConfig(level=logging.INFO)

BACKEND_URL = "https://localhost:8080/api/car-ads"


def url_already_processed(url: str) -> bool:
    with SessionLocal() as session:
        result = session.execute(
            text("SELECT 1 FROM ads_imported WHERE url = :url LIMIT 1"),
            {"url": url}
        ).fetchone()
        return result is not None


def mark_url_as_processed(url: str):
    with SessionLocal() as session:
        session.execute(
            text("INSERT INTO ads_imported (url) VALUES (:url) ON CONFLICT DO NOTHING"),
            {"url": url}
        )
        session.commit()


def check_new_ads(max_pages=10, stop_after_no_new=3):
    new_ads = 0
    pages_checked = 0
    consecutive_no_new = 0

    for page in range(1, max_pages + 1):
        ad_urls = get_ad_urls(page)
        pages_checked += 1
        page_new_ads = 0

        for url in ad_urls:
            if url_already_processed(url):
                continue

            try:
                features = fetch_and_extract_features(url)

                resp = requests.post(BACKEND_URL, json=features)
                resp.raise_for_status()

                mark_url_as_processed(url)
                page_new_ads += 1
                new_ads += 1
            except Exception as e:
                logger.error(f"Failed processing {url}: {e}")

        if page_new_ads == 0:
            consecutive_no_new += 1
        else:
            consecutive_no_new = 0

        if consecutive_no_new >= stop_after_no_new:
            logger.info(f"No new ads in {stop_after_no_new} consecutive pages. Stopping.")
            break

    logger.info(f"Done. Pages checked: {pages_checked}, New ads: {new_ads}")
