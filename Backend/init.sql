CREATE TYPE role AS ENUM ('ADMIN', 'USER');

CREATE TABLE app_users
(
    username                   VARCHAR(255) PRIMARY KEY,
    password                   VARCHAR(255),
    name                       VARCHAR(255),
    surname                    VARCHAR(255),
    is_account_non_expired     BOOLEAN DEFAULT TRUE,
    is_account_non_locked      BOOLEAN DEFAULT TRUE,
    is_credentials_non_expired BOOLEAN DEFAULT TRUE,
    is_enabled                 BOOLEAN DEFAULT TRUE,
    role                       role
);

CREATE TABLE IF NOT EXISTS ads_imported
(
    id         SERIAL PRIMARY KEY,
    url        TEXT UNIQUE NOT NULL,
    scraped_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ads
(
    id               BIGINT PRIMARY KEY,
    brand            TEXT NOT NULL,
    model            TEXT NOT NULL,
    year             INT  NOT NULL,
    fuelType         TEXT NOT NULL,
    kilometers       INT NOT NULL,
    transmission     TEXT NOT NULL,
    bodyType         TEXT NOT NULL,
    color            TEXT NOT NULL,
    registrationType TEXT NOT NULL,
    registeredUntil  DATE NOT NULL,
    enginePower      TEXT NOT NULL,
    emissionType     TEXT NOT NULL,
    description      TEXT,
--     price_eur        FLOAT,
--     location         TEXT NOT NULL,
--     engine_volume    FLOAT,
--     features         TEXT,
--     contact          TEXT,
    photo_url        TEXT NOT NULL,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS interactions
(
    interaction_id  BIGSERIAL PRIMARY KEY,
    user_username   TEXT        NOT NULL REFERENCES app_users (username) ON DELETE CASCADE,
    ad_id           BIGINT      NOT NULL REFERENCES ads (id) ON DELETE CASCADE,
    interactionType VARCHAR(12) NOT NULL,
    strength        INT         NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS preferences
(
    user_username TEXT   NOT NULL REFERENCES app_users (username) ON DELETE CASCADE,
    ad_id         BIGINT NOT NULL REFERENCES ads (id) ON DELETE CASCADE,
    rank          INT    NOT NULL,
    generated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_username, ad_id)
);

CREATE INDEX IF NOT EXISTS idx_interactions_user ON interactions (user_username);
CREATE INDEX IF NOT EXISTS idx_interactions_ad ON interactions (ad_id);