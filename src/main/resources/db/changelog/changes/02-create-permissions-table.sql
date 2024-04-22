-- Создание схемы permissions
CREATE SCHEMA IF NOT EXISTS permissions;

-- Создание таблицы ролей в схеме permissions
CREATE TABLE IF NOT EXISTS permissions.roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы прав в схеме permissions
CREATE TABLE IF NOT EXISTS permissions.rights
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы ролей в схеме permissions
CREATE TABLE permissions.user_training_types (
                                     id SERIAL PRIMARY KEY,
                                     user_id BIGINT NOT NULL,
                                     training_type VARCHAR(255) NOT NULL,
                                     FOREIGN KEY (user_id) REFERENCES main.users(id)
);
