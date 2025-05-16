from fastapi import FastAPI

from scraper import extract_from_first_n_pages

app = FastAPI()

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=8001, reload=True)

@app.get("/")
async def root():
    return {"message": "Hello World"}


@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message": f"Hello {name}"}

@app.get("/health")
async def health():
    return {"status": "ok"}

@app.post("/extract-all")
async def extract_all(pages: int = 10):
    return extract_from_first_n_pages(pages)


