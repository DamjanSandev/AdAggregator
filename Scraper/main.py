from fastapi import FastAPI
from scheduler import check_new_ads
from scraper import extract_from_first_n_pages
from apscheduler.schedulers.background import BackgroundScheduler
import requests

import os
from dotenv import load_dotenv


app = FastAPI()

load_dotenv()

SCRAPER_SECRET = os.getenv("SCRAPER_SECRET")
headers = {
    "X-SCRAPER-KEY": SCRAPER_SECRET
}
SPRING_BACKEND_URL = "http://spring-backend:9090/api/ads/add"

# if __name__ == "__main__":
#     import uvicorn
#     uvicorn.run("main:app", host="0.0.0.0", port=8001, reload=True)
@app.get("/")
async def root():
    return {"message": "Hello World"}


@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message": f"Hello {name}"}


@app.get("/health")
async def health():
    return {"status": "ok"}


@app.post("/scrape-all")
async def scrape_all(pages: int = 1):
    ads = extract_from_first_n_pages(pages)
    success_count = 0
    failed_count = 0

    for ad in ads:
        try:
            response = requests.post(SPRING_BACKEND_URL, json=ad,headers=headers)
            if response.status_code == 200:
                success_count += 1
            else:
                failed_count += 1
                print(f"Error code: {response.status_code}")
        except Exception as e:
            failed_count += 1
            print(f"Error posting ad: {e}")

    return {
        "message": f"Scraped {len(ads)} ads",
        "all ads":ads,
        "success_count": success_count,
        "failed_count": failed_count,
    }

# @app.on_event("startup")
# def start_scheduler():
#     scheduler = BackgroundScheduler()
#     scheduler.add_job(check_new_ads, trigger="interval", minutes=15)
#     scheduler.start()
