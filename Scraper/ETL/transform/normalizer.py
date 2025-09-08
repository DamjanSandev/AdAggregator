from bs4 import BeautifulSoup
from datetime import datetime
from constants.maps import AD_MAPS
from constants.selectors import PAGE_SELECTORS


def normalize_enum_type(mk_value: str, enum_map: dict) -> str:
    return enum_map.get(mk_value.strip(), "UNKNOWN")


def normalize_numeric(val: str) -> int:
    return int(val.replace(".", "").replace(",", "").strip())


def normalize_price(val: str) -> int:
    return int(val.replace(".", "").replace("€", "").replace("МКД", "").strip())


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


def normalize_structured_fields(soup: BeautifulSoup) -> dict:
    fields: dict = {}
    labels = soup.select(PAGE_SELECTORS['LABEL_SELECTOR'])
    values = soup.select(PAGE_SELECTORS['VALUE_SELECTOR'])
    for label, value in zip(labels, values):
        raw_key = label.get_text(strip=True).lower().rstrip(":")
        val = value.get_text(strip=True)
        key = AD_MAPS['FIELD_MAP'].get(raw_key)
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
                fields[key] = normalize_enum_type(val, AD_MAPS['FUEL_TYPE_MAP'])
            elif key == "transmission":
                fields[key] = normalize_enum_type(val, AD_MAPS['TRANSMISSION_MAP'])
            elif key == "registrationType":
                fields[key] = normalize_enum_type(val, AD_MAPS['REG_TYPE_MAP'])
            elif key == "emissionType":
                fields[key] = normalize_enum_type(val, AD_MAPS['EMISSION_TYPE_MAP'])
            elif key == "bodyType":
                fields[key] = normalize_enum_type(val, AD_MAPS['BODY_TYPE_MAP'])
            elif key == "registeredUntil":
                fields[key] = normalize_registered_until(val)
            else:
                fields[key] = val
        except Exception as e:
            print(f"Failed to normalize {key}: {val} — {e}")
    return fields


def normalize_and_extract_description(soup: BeautifulSoup) -> str | None:
    desc_el = soup.select_one(PAGE_SELECTORS['DESCRIPTION_SELECTOR'])
    return desc_el.get_text("\n", strip=True) if desc_el else None


def normalize_and_extract_photo_url(soup: BeautifulSoup) -> str | None:
    photo_el = soup.select_one(PAGE_SELECTORS['PHOTO_SELECTOR'])
    if photo_el:
        photo_url = photo_el.get("src")
        if photo_url.startswith("//"):
            return "https:" + photo_url
        elif photo_url.startswith("/"):
            return PAGE_SELECTORS['BASE_DOMAIN'] + photo_url
        return photo_url
    return None
