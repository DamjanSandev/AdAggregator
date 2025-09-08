import requests
from bs4 import FeatureNotFound
from lxml.etree import XMLSyntaxError
from ETL.extract.url_provider import get_urls_from_n_pages
from ETL.extract.parser import parse_ad_html
from ETL.load.loader import load_ads
from ETL.utils.logger import init_logger


def run_pipeline(number_pages: int = 1, store: bool = True) -> list[dict]:
    """
    Run the full ETL pipeline: extract → transform → load.
    Returns:
    List of dictionaries representing successfully parsed ads.
    Logs clear messages for all failed URLs.
    """
    logger = init_logger(__name__)
    ads_data: list[dict] = []
    failed_entries: list[dict] = []

    try:
        ad_urls = get_urls_from_n_pages(number_pages)
    except (requests.RequestException, ValueError) as e:
        logger.critical(f"Failed to get URLs from {number_pages} pages: {e}", exc_info=True)
        return []

    for ad_url in ad_urls:
        try:
            ad_data = parse_ad_html(ad_url)
            if not ad_data or not isinstance(ad_data, dict):
                raise ValueError("Parsed data is empty or invalid format")
            ads_data.append(ad_data)

        except requests.RequestException as e:
            failed_entries.append({"url": ad_url, "reason": f"Network error: {e}"})

        except (ValueError, TypeError, FeatureNotFound, XMLSyntaxError) as e:
            failed_entries.append({"url": ad_url, "reason": f"Parsing error: {e}"})

    # Log failed entries in a readable format
    if failed_entries:
        logger.info(f"\nFailed URLs ({len(failed_entries)}):")
        for fail in failed_entries:
            logger.info(f" - {fail['url']}: {fail['reason']}")

    if store and ads_data:
        load_ads(ads_data)

    return ads_data
