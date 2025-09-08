import os
from langchain.chat_models import ChatOpenAI
from dotenv import load_dotenv

load_dotenv()


def load_llm(max_tokens: int = None):
    model_kwargs = {
        "response_format": "json"
    }
    if max_tokens is not None:
        model_kwargs["max_tokens"] = str(max_tokens)

    return ChatOpenAI(
        #-2024-07-18
        model_name="gpt-4o-mini",
        temperature=0.1,
        openai_api_key=os.getenv("OPENAI_API_KEY"),
        model_kwargs=model_kwargs,
        max_retries=2,
    )
