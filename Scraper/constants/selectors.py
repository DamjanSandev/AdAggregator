# Ad urls
BASE_DOMAIN = "https://reklama5.mk"
BASE_URL = f"{BASE_DOMAIN}/Search?city=&cat=24&q="
LISTING_LINKS = ".SearchAdTitle"

# Structured
LABEL_SELECTOR = "div.col-5"
VALUE_SELECTOR = "div.col-7"

DESCRIPTION_SELECTOR = ("body > div.container.body-content > "
                        "div:nth-child(7) > div.row.mt-2 > div > div > div.card-body.px-0 > div:nth-child(4) "
                        "> div.col-8 > p:nth-child(3)")
PHOTO_SELECTOR = ".ad-image-preview-table img"
PRICE_SELECTOR = "h5.mb-0.defaultBlue"

PAGE_SELECTORS = {
    'BASE_DOMAIN': BASE_DOMAIN,
    'BASE_URL': BASE_URL,
    'LISTING_LINKS': LISTING_LINKS,
    'LABEL_SELECTOR': LABEL_SELECTOR,
    'VALUE_SELECTOR': VALUE_SELECTOR,
    'DESCRIPTION_SELECTOR': DESCRIPTION_SELECTOR,
    'PHOTO_SELECTOR': PHOTO_SELECTOR,
    'PRICE_SECTOR': PRICE_SELECTOR,
}
