import json
import re
from typing import Dict, Any
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM

MODEL_NAME = "google/mt5-small"
EXPECTED_KEYS = ["price_eur", "location", "engine_volume", "features", "contact"]

tokenizer = AutoTokenizer.from_pretrained(MODEL_NAME, use_fast=False)
model = AutoModelForSeq2SeqLM.from_pretrained(MODEL_NAME)

EXAMPLE_JSON = json.dumps({
    "price_eur": 14990,
    "location": "Скопје",
    "engine_volume": 1.5,
    "features": ["гаранција", "сервисна историја"],
    "contact": "078546171"
}, ensure_ascii=False, indent=2)

def build_prompt(desc: str) -> str:
    return (
        "Извлечи ги следните податоци од огласот за автомобил и врати ги како JSON:\n"
        "- price_eur (пример: 14990)\n"
        "- location (пример: Скопје)\n"
        "- engine_volume (пример: 1.5)\n"
        "- features (пример: [\"гаранција\", \"сервисна историја\"])\n"
        "- contact (пример: 078546171)\n\n"
        f"Пример:\n{EXAMPLE_JSON}\n\n"
        f"Опис на огласот:\n{desc}\n\n"
        "JSON:"
    )

_json_re = re.compile(r"\{.*\}", re.S)

def clean_description(text: str) -> str:
    text = re.sub(r"https?://\S+", "", text)
    text = re.sub(r"[^\w\sА-Ша-шЀ-ӿ€.,:/-]", "", text)
    text = re.sub(r"\n{2,}", "\n", text)
    return text.strip()[:800]

def _safe_json(text: str) -> Dict[str, Any]:
    match = _json_re.search(text)
    if not match:
        return {"error": "no-json", "raw_output": text}
    try:
        return json.loads(match.group(0))
    except json.JSONDecodeError:
        return {"error": "bad-json", "raw_output": text}

def _normalize(data: Dict[str, Any]) -> Dict[str, Any]:
    return {k: data.get(k, None) for k in EXPECTED_KEYS}

def extract_features(description: str) -> Dict[str, Any]:
    cleaned = clean_description(description)
    prompt = build_prompt(cleaned)
    input_ids = tokenizer(prompt, return_tensors="pt").input_ids
    output_ids = model.generate(
        input_ids,
        max_length=512,
        num_beams=4,
        early_stopping=True
    )
    decoded = tokenizer.decode(output_ids[0], skip_special_tokens=True)
    parsed = _safe_json(decoded)
    if "error" in parsed:
        return {**_normalize({}), **parsed}
    return _normalize(parsed)
