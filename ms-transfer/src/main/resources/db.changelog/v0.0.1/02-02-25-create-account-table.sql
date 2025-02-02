CREATE TABLE accounts
(
    id             SERIAL PRIMARY KEY,
    user_id        BIGINT NOT NULL,
    balance        NUMERIC NOT NULL DEFAULT 100.00,
    phone_number   VARCHAR(255) NOT NULL,
    birth_date     TIMESTAMP
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP
);