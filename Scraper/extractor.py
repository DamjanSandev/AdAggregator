import os
import json
import re
from typing import Dict, Any

from openai import OpenAI
from dotenv import load_dotenv

# Load environment variables
load_dotenv()
client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

EXPECTED_KEYS = ["location", "features", "contact_number", "contact_name"]

EXAMPLE_JSON = json.dumps({
    "location": "Скопје",
    "features": ["гаранција", "сервисна историја"],
    "contact_number": "078546171",
    "contact_name": "Дејан"
}, ensure_ascii=False, indent=2)

_JSON_RE = re.compile(r"\{.*\}", re.S)


def clean_description(text: str) -> str:
    text = re.sub(r"https?://\S+", "", text)
    text = re.sub(r"[^\w\sА-Ша-шЀ-ӿ€.,:/-]", "", text)
    text = re.sub(r"\n{2,}", "\n", text)
    return text.strip()[:1000]


def build_prompt(description: str) -> str:
    return (
        "Извлечи ги следните податоци од огласот за автомобил и врати ги како JSON.\n"
        "Нормализирај ги вредностите по овие правила:\n"
        "- location: користи латиница и прва буква голема (на пр. СКОПЈЕ → Skopje)\n"
        "- features: врати како листа од мали букви на кирилица, без дупликати\n"
        "- contact_number: само деветцифрен број (на пр. 070123456)\n"
        "- contact_name: прва буква голема, останатите мали и на латиница\n\n"
        "📌 Ако не може да се пронајде некое поле, врати `null`.\n"
        "---\n"
        "Опис:\n"
        "Се продава автомобил, се наоѓа во СКОПЈЕ. Има сервисна историја и нова гаранција. "
        "Сопственик Јован, тел: +389 70 123 456.\n"
        "---\n"
        "JSON:\n"
        "{\n"
        '  "location": "Skopje",\n'
        '  "features": ["сервисна историја", "гаранција"],\n'
        '  "contact_number": "070123456",\n'
        '  "contact_name": "Jovan"\n'
        "}\n\n"
        f"Опис на огласот:\n{description}\n\n"
        "JSON:"
    )


def _safe_json(response_text: str) -> Dict[str, Any]:
    match = _JSON_RE.search(response_text)
    if not match:
        return {"error": "no-json", "raw_output": response_text}
    try:
        return json.loads(match.group(0))
    except json.JSONDecodeError:
        return {"error": "bad-json", "raw_output": response_text}


def _normalize(data: Dict[str, Any]) -> Dict[str, Any]:
    return {key: data.get(key) for key in EXPECTED_KEYS}


def extract_features(description: str) -> Dict[str, Any]:
    cleaned_desc = clean_description(description)
    prompt = build_prompt(cleaned_desc)

    try:
        response = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {
                    "role": "system",
                    "content": "Ти си асистент кој вади JSON податоци од неструктуриран текст на македонски јазик."
                },
                {
                    "role": "user",
                    "content": prompt
                }
            ],
            temperature=0,
            max_tokens=512
        )

        reply = response.choices[0].message.content
        print("[🧠 GPT reply]", reply)
        parsed = _safe_json(reply)
        if "error" in parsed:
            return {**_normalize({}), **parsed}
        return _normalize(parsed)

    except Exception as e:
        return {"error": "openai-error", "message": str(e), **_normalize({})}