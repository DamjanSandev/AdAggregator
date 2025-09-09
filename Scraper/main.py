import os
import logging
from dotenv import load_dotenv
from fastapi import FastAPI, HTTPException
from ETL.pipeline import run_pipeline
from recommender.service import run_recommender_service

load_dotenv()

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(name)s - %(message)s"
)
logger = logging.getLogger(__name__)
app = FastAPI(title="Ad Scraper & Recommender API")

SCRAPER_SECRET = os.getenv("SCRAPER_SECRET")
SPRING_BACKEND_URL = os.getenv("SPRING_BACKEND_URL", "http://spring-backend:9090/api/ads/add")
headers = {"X-SCRAPER-KEY": SCRAPER_SECRET}


@app.post("/scrape-all")
async def scrape_all(pages: int = 1):
    """
    Run the ETL pipeline: scrape ads, transform, and send to backend.
    """
    try:
        logger.info("Starting ETL pipeline for %s pages...", pages)
        result = run_pipeline(number_pages=pages, store=True)
        logger.info("ETL pipeline finished. Scraped %s ads.", len(result))
        return {"scraped_ads": len(result), "data": result}
    except Exception as e:
        logger.exception("ETL pipeline failed")
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/refresh")
def refresh_recommendations():
    """
    Generate and publish content-based recommendations.
    """
    try:
        logger.info("Generating recommendations...")
        result = run_recommender_service()
        logger.info("Recommendation service finished.")
        return result
    except Exception as e:
        logger.exception("Recommendation generation failed")
        raise HTTPException(status_code=500, detail=str(e))

# Local dev/testing runner
# if __name__ == "__main__":
#     import uvicorn
#     uvicorn.run("main:app", host="0.0.0.0", port=8001, reload=True)
