import requests
from bs4 import BeautifulSoup
from extractor import extract_features

BASE_DOMAIN = "https://reklama5.mk"
BASE_URL = "https://reklama5.mk/Search?city=&cat=24&q="
LISTING_LINKS = ".SearchAdTitle"

LABEL_SELECTOR = "div.col-5"
VALUE_SELECTOR = "div.col-7"
PHOTO_SELECTOR = ".ad-image-preview-table img"
DESCRIPTION_SELECTOR = "div.card-body.px-0"

FIELD_MAP = {
    "марка": "make",
    "модел": "model",
    "година": "year",
    "гориво": "fuel_type",
    "километри": "kilometers",
    "менувач": "transmission",
    "боја": "color",
    "каросерија": "body_type",
    "регистрирана до": "registered_until",
    "сила на моторот": "power_kw"
}


def normalize_numeric(val: str) -> int:
    return int(val.replace(".", "").replace(",", "").strip())


def get_ad_urls(page: int = 1) -> list[str]:
    resp = requests.get(f"{BASE_URL}&page={page}")
    resp.raise_for_status()
    soup = BeautifulSoup(resp.text, "lxml")
    return [BASE_DOMAIN + a["href"] for a in soup.select(LISTING_LINKS)]


def parse_structured_fields(soup: BeautifulSoup) -> dict:
    fields = {}
    labels = soup.select(LABEL_SELECTOR)
    values = soup.select(VALUE_SELECTOR)

    for label, value in zip(labels, values):
        raw_key = label.get_text(strip=True).lower().rstrip(":")
        val = value.get_text(strip=True)
        key = FIELD_MAP.get(raw_key)

        if not key:
            continue

        try:
            if key == "year":
                fields[key] = int(val)
            elif key == "kilometers":
                fields[key] = normalize_numeric(val)
            elif key == "power_kw":
                fields[key] = int(val.split(" ")[0])
            else:
                fields[key] = val
        except Exception as e:
            print(f"Failed to parse {key}: {val} — {e}")

    return fields


def extract_description(soup: BeautifulSoup) -> str | None:
    desc_el = soup.select_one(DESCRIPTION_SELECTOR)
    if desc_el:
        return desc_el.get_text("\n", strip=True)
    return None


def enrich_with_llm_fields(description: str, base_fields: dict) -> dict:
    llm_json = extract_features(description)
    for key, value in llm_json.items():
        if key not in base_fields or not base_fields[key]:
            base_fields[key] = value
    return base_fields


def parse_photo_url(soup: BeautifulSoup) -> str | None:
    photo_el = soup.select_one(PHOTO_SELECTOR)
    if photo_el:
        photo_url = photo_el.get("src")
        if photo_url.startswith("//"):
            return "https:" + photo_url
        elif photo_url.startswith("/"):
            return BASE_DOMAIN + photo_url
        return photo_url
    return None


def fetch_and_extract_features(ad_url: str) -> dict:
    resp = requests.get(ad_url)
    resp.raise_for_status()
    soup = BeautifulSoup(resp.text, "lxml")

    fields = {"url": ad_url}
    fields.update(parse_structured_fields(soup))

    description = extract_description(soup)
    if description:
        fields["description"] = description
        fields = enrich_with_llm_fields(description, fields)

    photo_url = parse_photo_url(soup)
    if photo_url:
        fields["photo_url"] = photo_url

    return fields


def get_first_n_urls(n: int = 10) -> list[str]:
    urls: list[str] = []
    for p in range(1, n + 1):
        urls.extend(get_ad_urls(p))
    return urls


# TODO change n=10
def extract_from_first_n_pages(n: int = 2) -> list[dict]:
    enriched_ads: list = []
    urls = get_first_n_urls(n)
    for url in urls:
        try:
            full_features = fetch_and_extract_features(url)
            enriched_ads.append(full_features)
        except Exception as e:
            print(f"Failed to extract {url}: {e}")
    return enriched_ads
