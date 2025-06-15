from fastapi import FastAPI
from scheduler import check_new_ads
from scraper import extract_from_first_n_pages
from apscheduler.schedulers.background import BackgroundScheduler

app = FastAPI()

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
    return extract_from_first_n_pages(pages)

# @app.on_event("startup")
# def start_scheduler():
#     scheduler = BackgroundScheduler()
#     scheduler.add_job(check_new_ads, trigger="interval", minutes=15)
#     scheduler.start()
