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
    scraped_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
