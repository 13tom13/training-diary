-- Добавление тестового пользователя
INSERT INTO main.users (first_name, last_name, email, password)
VALUES ('Иван', 'Петров', 'test@mail.ru', 'pass');

-- Добавление роли для тестового пользователя
INSERT INTO service.users_roles (user_id, role_id)
SELECT id, role_id
FROM (SELECT main.users.id, permissions.roles.id AS role_id
      FROM main.users
               CROSS JOIN permissions.roles
      WHERE users.email = 'test@mail.ru'
        AND roles.name = 'USER') AS user_roles_query;


-- Добавление прав для тестового пользователя
INSERT INTO service.user_rights (user_id, right_id)
SELECT id, right_id
FROM (SELECT main.users.id, permissions.rights.id AS right_id
      FROM main.users
               CROSS JOIN permissions.rights
      WHERE users.email = 'test@mail.ru') AS user_rights_query;

-- Добавление тренировок
INSERT INTO main.trainings (name, date, duration, calories_burned)
VALUES ('Кардио', TO_DATE('13.04.24', 'DD.MM.YY'), 90, 560),
       ('Силовая', TO_DATE('14.04.24', 'DD.MM.YY'), 60, 450),
       ('Гибкость', TO_DATE('10.04.24', 'DD.MM.YY'), 45, 300);

-- Добавление тренировок пользователя
INSERT INTO service.user_trainings (user_id, training_id)
SELECT u.id, t.id
FROM main.users u
         JOIN main.trainings t ON
    (t.name = 'Кардио' AND t.date = TO_DATE('13.04.24', 'DD.MM.YY')) OR
    (t.name = 'Силовая' AND t.date = TO_DATE('14.04.24', 'DD.MM.YY')) OR
    (t.name = 'Гибкость' AND t.date = TO_DATE('10.04.24', 'DD.MM.YY'))
WHERE u.email = 'test@mail.ru';
