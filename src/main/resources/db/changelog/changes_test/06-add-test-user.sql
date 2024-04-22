-- Добавление тестового пользователя
INSERT INTO main.users (first_name, last_name, email, password)
VALUES ('John', 'Doe', 'test@test.com', 'password');

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
      WHERE users.email = 'test@test.com') AS user_rights_query;
