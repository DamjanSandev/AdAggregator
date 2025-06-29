import requests
from bs4 import BeautifulSoup
from extractor import extract_features
from datetime import datetime


BASE_DOMAIN = "https://reklama5.mk"
BASE_URL = "https://reklama5.mk/Search?city=&cat=24&q="
LISTING_LINKS = ".SearchAdTitle"

LABEL_SELECTOR = "div.col-5"
VALUE_SELECTOR = "div.col-7"
PHOTO_SELECTOR = ".ad-image-preview-table img"
DESCRIPTION_SELECTOR = ("body > div.container.body-content > "
                        "div:nth-child(7) > div.row.mt-2 > div > div > div.card-body.px-0 > div:nth-child(4) "
                        "> div.col-8 > p:nth-child(3)")
PRICE_SELECTOR = "h5.mb-0.defaultBlue"

FIELD_MAP = {
    "марка": "brand",
    "модел": "model",
    "година": "year",
    "гориво": "fuelType",
    "километри": "kilometers",
    "менувач": "transmission",
    "каросерија": "bodyType",
    "боја": "color",
    "регистрација": "registrationType",
    "регистрирана до": "registeredUntil",
    "сила на моторот": "enginePower",
    "класа на емисија": "emissionType",
}

FUEL_TYPE_MAP = {
    "Дизел": "DIESEL",
    "Бензин": "PETROL",
    "Бензин / Плин": "PETROL_LPG",
    "Хибрид (Дизел / Електро)": "HYBRID_DIESEL_ELECTRIC",
    "Хибрид (Бензин / Електро)": "HYBRID_PETROL_ELECTRIC",
    "Електричен автомобил": "ELECTRIC"
}

TRANSMISSION_MAP = {
    "Рачен": "MANUAL",
    "Автоматски": "AUTOMATIC",
    "Полуавтоматски": "SEMI_AUTOMATIC"
}

REG_TYPE_MAP = {
    "Македонска": "MACEDONIAN",
    "Странска": "FOREIGN"
}

EMISSION_TYPE_MAP = {
    "Еуро 1": "EURO1",
    "Еуро 2": "EURO2",
    "Еуро 3": "EURO3",
    "Еуро 4": "EURO4",
    "Еуро 5": "EURO5",
    "Еуро 6": "EURO6",
    "Останато": "OTHER"
}

BODY_TYPE_MAP = {
    "Maли градски": "SMALL_CITY_CAR",
    "Хеџбек": "HATCHBACK",
    "Седани": "SEDAN",
    "Каравани": "CARAVAN",
    "Моноволумен": "MPV",
    "Теренци - SUV": "SUV",
    "Кабриолети": "CABRIOLET",
    "Купеа": "COUPE",
    "Останато": "OTHER"
}


def normalize_enum_type(mk_value: str, enum_map: dict) -> str:
    return enum_map.get(mk_value.strip(), "UNKNOWN")

def normalize_numeric(val: str) -> int:
    return int(val.replace(".", "").replace(",", "").strip())

def normalize_registered_until(raw: str) -> str:
    raw = raw.strip()
    try:
        return datetime.strptime(raw, "%d.%m.%Y").strftime("%Y-%m-%d")
    except ValueError:
        pass

    try:
        return datetime.strptime(raw, "%m.%Y").replace(day=1).strftime("%Y-%m-%d")
    except ValueError:
        return raw

def get_ad_urls(page: int = 1) -> list[str]:
    resp = requests.get(f"{BASE_URL}&page={page}")
    resp.raise_for_status()
    soup = BeautifulSoup(resp.text, "lxml")
    return [BASE_DOMAIN + a["href"] for a in soup.select(LISTING_LINKS)]


def normalize_price(val: str) -> int:
    val = val.replace(".", "").replace("€", "").strip()
    return int(val)

def parse_price(soup: BeautifulSoup) -> int | None:
    price_el = soup.select_one(PRICE_SELECTOR)
    if price_el:
        try:
            return normalize_price(price_el.get_text())
        except Exception as e:
            print(f"Failed to parse price: {e}")
    return None


def parse_structured_fields(soup: BeautifulSoup) -> dict:
    fields = {}
    labels = soup.select(LABEL_SELECTOR)
    values = soup.select(VALUE_SELECTOR)

    for label, value in zip(labels, values):
        raw_key = label.get_text(strip=True).lower().rstrip(":")
        raw_key = raw_key.translate(str.maketrans("k", "к"))
        val = value.get_text(strip=True)
        key = FIELD_MAP.get(raw_key)

        if not key:
            continue

        try:
            if key == "year":
                fields[key] = int(val)
            elif key == "kilometers":
                fields[key] = normalize_numeric(val)
            elif key == "enginePower":
                fields[key] = val.split(" ")[0]
            elif key == "fuelType":
                fields[key] = normalize_enum_type(val, FUEL_TYPE_MAP)
            elif key == "transmission":
                fields[key] = normalize_enum_type(val, TRANSMISSION_MAP)
            elif key == "registrationType":
                fields[key] = normalize_enum_type(val, REG_TYPE_MAP)
            elif key == "emissionType":
                fields[key] = normalize_enum_type(val, EMISSION_TYPE_MAP)
            elif key == "bodyType":
                fields[key] = normalize_enum_type(val, BODY_TYPE_MAP)
            elif key == "registeredUntil":
                fields[key] = normalize_registered_until(val)
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

    price = parse_price(soup)
    if price is not None:
        fields["price"] = price

    fields.update(parse_structured_fields(soup))

    description = extract_description(soup)
    if description:
        fields["description"] = description
        # fields = enrich_with_llm_fields(description, fields)

    photo_url = parse_photo_url(soup)
    if photo_url:
        fields["photoUrl"] = photo_url

    return fields


def get_first_n_urls(n: int = 10) -> list[str]:
    urls: list[str] = []
    for p in range(1, n + 1):
        urls.extend(get_ad_urls(p))
    return urls


# TODO change n=10
def extract_from_first_n_pages(n: int = 1) -> list[dict]:
    enriched_ads: list = []
    urls = get_first_n_urls(n)
    for url in urls:
        try:
            full_features = fetch_and_extract_features(url)
            enriched_ads.append(full_features)
        except Exception as e:
            print(f"Failed to extract {url}: {e}")
    return enriched_ads
