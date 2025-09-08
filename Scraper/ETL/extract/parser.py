import requests
from ETL.extract.LLM.ai_extractor import extract_fields_from_text
from ETL.transform.normalizer import normalize_structured_fields, normalize_and_extract_description, normalize_price, \
    normalize_and_extract_photo_url
from constants.selectors import PRICE_SELECTOR
from bs4 import BeautifulSoup


def fetch_ad_html(ad_url: str) -> BeautifulSoup:
    resp = requests.get(ad_url)
    resp.raise_for_status()
    return BeautifulSoup(resp.text, "lxml")


def parse_ad_html(ad_url: str) -> dict:
    soup = fetch_ad_html(ad_url)
    fields: dict = {"url": ad_url}

    # Normalized structured fields like year, kilometers, engine_power...
    structured = normalize_structured_fields(soup)
    fields.update(structured)

    # Normalized description
    # TODO it has to be structured in way better format on macedonian...
    description = normalize_and_extract_description(soup)
    if description:
        fields["description"] = description

        # OpenAI structuring-LLM in json
        # TODO test it on separated way
        # llm_result_field = extract_fields_from_text(description)
        # fields.update(llm_result_field)

    # Normalized ad price
    price = soup.select_one(PRICE_SELECTOR).get_text()
    if price:
        fields["price"]: int = normalize_price(price)

    # Normalized and parsed ad item photo
    photo_url = normalize_and_extract_photo_url(soup)
    if photo_url:
        fields["photoUrl"] = photo_url

    return fields
