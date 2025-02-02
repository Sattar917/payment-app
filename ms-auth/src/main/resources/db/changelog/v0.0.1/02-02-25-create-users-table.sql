CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    surname    VARCHAR(255) NOT NULL,
    birth_date DATE         NOT NULL,
    password   VARCHAR(255) NOT NULL,
    username VARCHAR(255) UNIQUE
);