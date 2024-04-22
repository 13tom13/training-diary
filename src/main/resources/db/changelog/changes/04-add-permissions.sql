-- Вставка данных в таблицу roles
INSERT INTO permissions.roles (name) VALUES ('ADMIN'),('USER');

-- Вставка данных в таблицу rights
INSERT INTO permissions.rights (name) VALUES ('WRITE'), ('EDIT'), ('DELETE'), ('STATISTICS');

