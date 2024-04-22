-- Создание схемы service
CREATE SCHEMA IF NOT EXISTS public;

-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS public.users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    is_active  BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Создание таблицы тренировок
CREATE TABLE public.trainings
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    date            DATE NOT NULL,
    duration        INT NOT NULL,
    calories_burned INT NOT NULL,
    CONSTRAINT unique_name_date UNIQUE (name, date)
);


-- Создание таблицы для дополнительной информации о тренировках
CREATE TABLE public.training_additions
(
    training_id INT,
    key         VARCHAR(100),
    value       VARCHAR(255),
    PRIMARY KEY (training_id, key),
    FOREIGN KEY (training_id)
        REFERENCES trainings(id)
        ON DELETE CASCADE
);

-- Создание таблицы ролей в схеме service
CREATE TABLE IF NOT EXISTS public.roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы прав в схеме service
CREATE TABLE IF NOT EXISTS public.rights
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);
