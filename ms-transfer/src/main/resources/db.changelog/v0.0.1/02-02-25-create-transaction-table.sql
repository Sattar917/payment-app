CREATE TABLE transactions
(
    id                 SERIAL PRIMARY KEY,
    user_id            BIGINT       NOT NULL,
    type               VARCHAR(255) NOT NULL,
    amount             NUMERIC      NOT NULL,
    parent_transaction BIGINT,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP
);