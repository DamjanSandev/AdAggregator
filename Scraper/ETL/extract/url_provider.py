import requests
from bs4 import BeautifulSoup
from constants.selectors import BASE_DOMAIN, BASE_URL, LISTING_LINKS


def get_ad_urls(page: int = 1) -> list[str]:
    resp = requests.get(f"{BASE_URL}&page={page}")
    resp.raise_for_status()
    soup = BeautifulSoup(resp.text, "lxml")
    return [BASE_DOMAIN + a["href"] for a in soup.select(LISTING_LINKS)]


def get_urls_from_n_pages(n: int = 1) -> list[str]:
    urls: list[str] = []
    for p in range(1, n + 1):
        urls.extend(get_ad_urls(p))
    return urls
