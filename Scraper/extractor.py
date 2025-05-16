import json

from transformers import AutoTokenizer, AutoModelForSeq2SeqLM

MODEL_NAME = "google/mt5-small"
tokenizer = AutoTokenizer.from_pretrained(MODEL_NAME,use_fast=False)
model = AutoModelForSeq2SeqLM.from_pretrained(MODEL_NAME)


def extract_feautres(description: str) -> dict:
    prompt = (
        "Извлечи карактеристики на возило како JSON од овој опис:\n\n"
        f"{description}\n\n"
        "JSON:"
    )
    inputs = tokenizer(prompt, return_tensors="pt").input_ids
    outputs = model.generate(inputs,max_length=512, num_beans=4, early_stopping=True)
    result=tokenizer.decode(outputs[0],skip_special_tokens=True)
    return json.loads(result)
