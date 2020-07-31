/*
Создаем базу данных test
DROP DATABASE IF EXISTS test;
CREATE DATABASE test;

Подключаемся к test, создаем схему currency
\c test;
DROP SCHEMA IF EXISTS currency;
CREATE SCHEMA currency;

Создаем пользователя и настраиваем ему права доступа к базе данных test
CREATE ROLE financier WITH PASSWORD 'financier' LOGIN;
GRANT ALL ON SCHEMA currency TO financier;*/

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id       SMALLSERIAL,
    password VARCHAR(255),
    email    VARCHAR(255) UNIQUE,
    login    VARCHAR(255),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
    id   SMALLSERIAL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles
(
    user_id SMALLINT NOT NULL,
    role_id SMALLINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (role_id)
        REFERENCES roles (id)
);

INSERT INTO roles (name)
VALUES ('ROLE_USER');

INSERT INTO users (password, login, email)
VALUES ('$2y$12$G3.H5B2vrDOfmrwl/he/8.IZ12zNXHqmRbm2FVoMiDdqkOHZCf8vW', 'admin', 'admin@gmail.com');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);

DROP TABLE IF EXISTS quotes;
CREATE TABLE quotes
(
    id          SMALLSERIAL,
    currency_id VARCHAR(7),
    num_code    SMALLINT,
    char_code   VARCHAR(3),
    nominal     SMALLINT,
    name        VARCHAR(255),
    value       NUMERIC(7, 4),
    date        DATE,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS history;
CREATE TABLE history
(
    id           SMALLSERIAL,
    src_currency VARCHAR(255),
    dst_currency VARCHAR(255),
    src_sum      INT,
    dst_sum      NUMERIC(12, 2),
    date         DATE,
    user_id      SMALLINT,
    PRIMARY KEY (id)
);