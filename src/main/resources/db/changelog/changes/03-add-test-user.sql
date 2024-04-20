-- Добавление тестового пользователя
INSERT INTO users (first_name, last_name, email, password)
VALUES ('Иван', 'Петров', 'test@mail.ru', 'pass');

-- Добавление роли для тестового пользователя
INSERT INTO users_roles (user_id, role_id)
SELECT id, role_id
FROM (SELECT users.id, roles.id AS role_id
      FROM users
               CROSS JOIN roles
      WHERE users.email = 'test@mail.ru'
        AND roles.name = 'USER') AS user_roles_query;


-- Добавление прав для тестового пользователя
INSERT INTO user_rights (user_id, right_id)
SELECT id, right_id
FROM (SELECT users.id, rights.id AS right_id
      FROM users
               CROSS JOIN rights
      WHERE users.email = 'test@mail.ru') AS user_rights_query;

-- Добавление тренировок
INSERT INTO trainings (name, date, duration, calories_burned)
VALUES ('Кардио', '13.04.24', 90, 560),
       ('Силовая', '14.04.24', 60, 450),
       ('Гибкость', '10.04.24', 45, 300);

-- Добавление тренировок пользователя
INSERT INTO user_trainings (user_id, training_id)
SELECT u.id, t.id
FROM users u
         JOIN trainings t ON
    (t.name = 'Кардио' AND t.date = '13.04.24') OR
    (t.name = 'Силовая' AND t.date = '14.04.24') OR
    (t.name = 'Гибкость' AND t.date = '10.04.24')
WHERE u.email = 'test@mail.ru';
