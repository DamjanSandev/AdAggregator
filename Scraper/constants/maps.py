FIELD_MAP = {
    "марка": "brand",
    "модел": "model",
    "година": "year",
    "гориво": "fuelType",
    "километри": "kilometers",
    "менувач": "transmission",
    "каросерија": "bodyType",
    "боја": "color",
    "регистрација": "registrationType",
    "регистрирана до": "registeredUntil",
    "сила на моторот": "enginePower",
    "класа на емисија": "emissionType",
}

FUEL_TYPE_MAP = {
    "Дизел": "DIESEL",
    "Бензин": "PETROL",
    "Бензин / Плин": "PETROL_LPG",
    "Хибрид (Дизел / Електро)": "HYBRID_DIESEL_ELECTRIC",
    "Хибрид (Бензин / Електро)": "HYBRID_PETROL_ELECTRIC",
    "Електричен автомобил": "ELECTRIC"
}

TRANSMISSION_MAP = {
    "Рачен": "MANUAL",
    "Автоматски": "AUTOMATIC",
    "Полуавтоматски": "SEMI_AUTOMATIC"
}
REG_TYPE_MAP = {
    "Македонска": "MACEDONIAN",
    "Странска": "FOREIGN"
}

EMISSION_TYPE_MAP = {
    "Еуро 1": "EURO1",
    "Еуро 2": "EURO2",
    "Еуро 3": "EURO3",
    "Еуро 4": "EURO4",
    "Еуро 5": "EURO5",
    "Еуро 6": "EURO6",
    "Останато": "OTHER"
}

BODY_TYPE_MAP = {
    "Maли градски": "SMALL_CITY_CAR",
    "Хеџбек": "HATCHBACK",
    "Седани": "SEDAN",
    "Каравани": "CARAVAN",
    "Моноволумен": "MPV",
    "Теренци - SUV": "SUV",
    "Кабриолети": "CABRIOLET",
    "Купеа": "COUPE",
    "Останато": "OTHER"
}

AD_MAPS = {
    'FIELD_MAP': FIELD_MAP,
    'FUEL_TYPE_MAP': FUEL_TYPE_MAP,
    'TRANSMISSION_MAP': TRANSMISSION_MAP,
    'REG_TYPE_MAP': REG_TYPE_MAP,
    'EMISSION_TYPE_MAP': EMISSION_TYPE_MAP,
    'BODY_TYPE_MAP': BODY_TYPE_MAP,
}
