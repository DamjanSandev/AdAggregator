import os
from dotenv import load_dotenv

load_dotenv()
BACKEND_PREF_ENDPOINT = os.getenv("BACKEND_PREF_ENDPOINT")
SCRAPER_SECRET = os.getenv("SCRAPER_SECRET")
HEADERS = {
    "X-SCRAPER-KEY": SCRAPER_SECRET
}
TOP_N_RECS = int(os.getenv("TOP_N_RECS", 10))
