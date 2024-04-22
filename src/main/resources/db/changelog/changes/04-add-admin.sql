-- Добавление администратора
INSERT INTO users (first_name, last_name, email, password)
VALUES ('Админ', 'Администратор', 'admin@mail.ru', 'admin');

-- Добавление роли для администратора
INSERT INTO service.users_roles (user_id, role_id)
SELECT id, role_id
FROM (SELECT users.id, roles.id AS role_id
      FROM users
               CROSS JOIN roles
      WHERE users.email = 'admin@mail.ru'
        AND roles.name = 'ADMIN') AS user_roles_query;


-- Добавление прав для администратора
INSERT INTO service.user_rights (user_id, right_id)
SELECT id, right_id
FROM (SELECT users.id, rights.id AS right_id
      FROM users
               CROSS JOIN rights
      WHERE users.email = 'admin@mail.ru') AS user_rights_query;