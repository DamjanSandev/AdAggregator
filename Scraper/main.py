import os
from dotenv import load_dotenv
from fastapi import FastAPI, HTTPException

from ETL.pipeline import run_pipeline
from recommender.recommender import generate_recommendations

load_dotenv()

app = FastAPI()

# Load secrets and backend URL
SCRAPER_SECRET = os.getenv("SCRAPER_SECRET")
SPRING_BACKEND_URL = os.getenv("SPRING_BACKEND_URL", "http://spring-backend:9090/api/ads/add")

headers = {
    "X-SCRAPER-KEY": SCRAPER_SECRET
}


@app.post("/scrape-all")
async def scrape_all(pages: int = 1):
    """
    Endpoint to run full ETL pipeline: scrape, transform, and optionally store ads.
    """
    try:
        # store=True ensures ads are sent to Spring backend
        result = run_pipeline(number_pages=pages, store=True)
        return {
            "scraped_ads": len(result),
            "data": result
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/refresh")
def refresh_recommendations():
    """
    Endpoint to generate content-based recommendations for users.
    """
    try:
        return generate_recommendations()
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


# Uncomment for local testing
# if __name__ == "__main__":
#     import uvicorn
#     uvicorn.run("main:app", host="0.0.0.0", port=8001, reload=True)
