-- Создание схемы service
CREATE SCHEMA IF NOT EXISTS service;


-- Создание таблицы для связи пользователей и их ролей (многие ко многим) в схеме service
CREATE TABLE IF NOT EXISTS service.users_roles
(
    USER_ID BIGINT,
    ROLE_ID BIGINT,
    FOREIGN KEY (USER_ID) REFERENCES public.users (id) ON DELETE CASCADE,
    FOREIGN KEY (ROLE_ID) REFERENCES public.roles (id) ON DELETE CASCADE,
    PRIMARY KEY (USER_ID, ROLE_ID)
);

-- Создание таблицы для связи пользователей и их прав (многие ко многим) в схеме service
CREATE TABLE IF NOT EXISTS service.user_rights
(
    user_id  INT NOT NULL,
    right_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE CASCADE,
    FOREIGN KEY (right_id) REFERENCES public.rights (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, right_id)
);

-- Создание таблицы пользовательских тренировок в стандартной схеме
CREATE TABLE IF NOT EXISTS user_trainings
(
    user_id     SERIAL REFERENCES public.users (id) ON DELETE CASCADE,
    training_id SERIAL REFERENCES public.trainings (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, training_id)
);