-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    is_active  BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Создание таблицы ролей
CREATE TABLE IF NOT EXISTS roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы прав
CREATE TABLE IF NOT EXISTS rights
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы для связи пользователей и их ролей (многие ко многим)
CREATE TABLE IF NOT EXISTS users_roles
(
    USER_ID BIGINT,
    ROLE_ID BIGINT,
    FOREIGN KEY (USER_ID) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (ROLE_ID) REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (USER_ID, ROLE_ID)
);

-- Создание таблицы для связи пользователей и их прав (многие ко многим)
CREATE TABLE user_rights
(
    user_id  INT NOT NULL,
    right_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (right_id) REFERENCES rights (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, right_id)
);

-- Создание таблицы тренировок
CREATE TABLE trainings
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    date            VARCHAR(100) NOT NULL,
    duration        INT          NOT NULL,
    calories_burned INT          NOT NULL
);

-- Создание таблицы для дополнительной информации о тренировках
CREATE TABLE training_additions
(
    training_id INT REFERENCES trainings (id),
    key         VARCHAR(100),
    value       VARCHAR(255),
    PRIMARY KEY (training_id, key)
);

CREATE TABLE IF NOT EXISTS user_trainings
(
    user_id     SERIAL REFERENCES users (id) ON DELETE CASCADE,
    training_id SERIAL REFERENCES trainings (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, training_id)
);