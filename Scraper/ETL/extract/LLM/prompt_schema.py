from langchain.prompts import (
    ChatPromptTemplate,
    FewShotChatMessagePromptTemplate,
    SystemMessagePromptTemplate,
    HumanMessagePromptTemplate
)


def get_system_template() -> str:
    """
    Returns the system prompt template as a string.
    """
    return """
You are a trusted AI extractor. Your job is to extract only factual, structured data from **unstructured car ad descriptions** written in **Macedonian Latin**.

🔧 Output must be in **JSON format**, strictly matching this structure:
{
  "location": string | null,
  "features": list[string] | [],
  "contact_number": string | null,
  "contact_name": string | null
}

🧾 Field-level normalization rules:
- "location": Format to title case in **Latin alphabet** (e.g., "SKOPJE" → "Skopje")
- "features": List of lowercased **Macedonian Cyrillic** keywords (no duplicates)
- "contact_number": 9-digit mobile number starting with 07X (e.g., 070123456). Remove country codes like +389
- "contact_name": Person’s first name, first letter uppercase, Latin letters only

⚠️ STRICT INSTRUCTIONS:
- Only include information that is explicitly stated in the text
- DO NOT GUESS OR INFER anything not clearly present
- If a field is missing, set its value to `null` (for strings) or `[]` (for lists)
- Do NOT return anything other than the JSON object

Now follow the instructions and examples carefully.

Return a raw JSON object as your **only** response. Do not add explanations, markdown, or text outside the JSON block.
"""


def few_shot_examples() -> list[dict]:
    """
    Returns the list of example input-output pairs for few-shot learning.
    """
    return [
        {
            "input": "Golf 5, 1.9 TDI, dobra sostojba, registriran do kraj na godinata. Se naogja vo Skopje. Cena dogovor. Kontakt 078123456",
            "output": {
                "location": "Skopje",
                "features": ["добра состојба", "регистриран до крај на годината"],
                "contact_number": "078123456",
                "contact_name": None
            }
        },
        {
            "input": "Citroen C3, 2008 godina, 1.4 benzinski motor, vo odlicna sostojba. Kontakt tel: 070 333 111, Tetovo",
            "output": {
                "location": "Tetovo",
                "features": ["одлична состојба"],
                "contact_number": "070333111",
                "contact_name": None
            }
        },
        {
            "input": "Se prodava vozilo vo Bitola, so servisna istorija i nov akumulator. Sopstvenik Dejan. Tel. 071-456-999",
            "output": {
                "location": "Bitola",
                "features": ["сервисна историја", "нов акумулатор"],
                "contact_number": "071456999",
                "contact_name": "Dejan"
            }
        },
        {
            "input": "Super vozilo bez malku greska, Cena dogovor. Tel: 070000000",
            "output": {
                "location": None,
                "features": ["без малку грешка"],
                "contact_number": "070000000",
                "contact_name": None
            }
        }
    ]


def build_example_template() -> ChatPromptTemplate:
    """
    Creates a ChatPromptTemplate for a single example with human and AI messages.
    """
    return ChatPromptTemplate.from_messages([
        ("human", "{input}"),
        ("ai", "{output}")
    ])


def create_few_shot_prompt(examples: list[dict]) -> FewShotChatMessagePromptTemplate:
    """
    Creates the FewShotChatMessagePromptTemplate from the examples.
    """
    example_prompt = build_example_template()
    return FewShotChatMessagePromptTemplate(
        examples=examples,
        example_prompt=example_prompt
    )


def get_prompt() -> ChatPromptTemplate:
    """
    Builds the final ChatPromptTemplate incorporating system message,
    few-shot examples, and user input.
    """
    system_message = SystemMessagePromptTemplate.from_template(get_system_template())
    few_shot_prompt = create_few_shot_prompt(few_shot_examples())
    human_message = HumanMessagePromptTemplate.from_template("{input}")

    return ChatPromptTemplate.from_messages([
        system_message,
        few_shot_prompt,
        human_message
    ])
