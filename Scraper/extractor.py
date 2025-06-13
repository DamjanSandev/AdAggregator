import json
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM

MODEL_NAME = "google/mt5-small"
tokenizer = AutoTokenizer.from_pretrained(MODEL_NAME, use_fast=False)
model = AutoModelForSeq2SeqLM.from_pretrained(MODEL_NAME)


def build_prompt(description: str) -> str:
    return (
        "Извлечи ги следните карактеристики на возилото од описот и врати ги како JSON:\n"
        "- цена во евра (price_eur)\n"
        "- локација (location)\n"
        "- волумен на моторот во литри (engine_volume)\n"
        "- листа на дополнителна опрема (features)\n"
        "- контакт телефон или вибер (contact)\n\n"
        f"Опис: {description}\n\n"
        "JSON:"
    )


def extract_features(description: str) -> dict:
    prompt = build_prompt(description)
    inputs = tokenizer(prompt, return_tensors="pt").input_ids

    outputs = model.generate(
        inputs,
        max_length=512,
        num_beams=4,
        early_stopping=True
    )

    result = tokenizer.decode(outputs[0], skip_special_tokens=True)

    try:
        return json.loads(result)
    except json.JSONDecodeError:
        return {"error": "Could not parse JSON", "raw_output": result}
