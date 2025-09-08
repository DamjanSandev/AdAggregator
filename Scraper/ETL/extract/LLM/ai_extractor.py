from typing import Dict, Any
from langchain.chat_models import ChatOpenAI
from langchain.output_parsers import JsonOutputParser
from langchain.prompts import ChatPromptTemplate

from ETL.extract.LLM.prompt_schema import get_prompt
from ETL.extract.LLM.llm_config import load_llm


def extract_fields_from_text(description: str) -> Dict[str, Any]:
    """
    Extract structured car ad metadata using LangChain LLM and prompt engineering.
    Uses basic langchain only (no langchain_core).
    """
    #??
    max_tokens = 512
    llm_model = load_llm()
    prompt: ChatPromptTemplate = get_prompt()
    parser = JsonOutputParser()

    try:
        messages = prompt.format_messages(input=description)
        output = llm_model.invoke(messages)
        return parser.parse(output.content)

    except Exception as e:
        return {
            "location": None,
            "features": [],
            "contact_number": None,
            "contact_name": None,
            "llm_error": f"LLM error: {str(e)}"
        }
