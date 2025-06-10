import requests
from bs4 import BeautifulSoup
from extractor import extract_feautres

BASE_DOMAIN = "https://reklama5.mk"
BASE_URL = "https://reklama5.mk/Search?city=&cat=24&q="
LISTING_LINKS = ".SearchAdTitle"
DESCRIPTION_SEL = "body > div.container.body-content > div:nth-child(7) > div.row.mt-2 > div > div > div.card-body.px-0 > div:nth-child(4) > div.col-8 > p:nth-child(3)"


def get_ad_urls(page: int = 1) -> list[str]:
    resp = requests.get(f"{BASE_URL}&page={page}")
    resp.raise_for_status()
    soup = BeautifulSoup(resp.text, "lxml")
    return [BASE_DOMAIN + a["href"] for a in soup.select(LISTING_LINKS)]


def fetch_description(ad_url: str) -> str:
    resp = requests.get(ad_url)
    resp.raise_for_status()
    soup = BeautifulSoup(resp.text, "lxml")
    return soup.select(DESCRIPTION_SEL).get_text("\n", strip=True)


def get_first_n_urls(n: int = 10) -> list[str]:
    urls: list[str] = []
    for p in range(1, n + 1):
        urls.extend(get_ad_urls(p))
    return urls


def extract_from_first_n_pages(n: int = 10) -> list[dict]:
    features: list[dict] = []
    urls = get_first_n_urls(n)
    for url in urls:
        desc = fetch_description(url)
        feat = extract_feautres(desc)
        features.append(feat)
    return features
