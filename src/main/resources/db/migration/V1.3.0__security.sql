CREATE TABLE users
(
	id_users INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL UNIQUE,
	password VARCHAR(80) NOT NULL,
	manager  VARCHAR(80) NOT NULL
);

create table roles
(
	id_roles  INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users_roles
(
	user_id INT NOT NULL,
	role_id INT NOT NULL,
	PRIMARY KEY (user_id, role_id),
	FOREIGN KEY (user_id) REFERENCES users (id_users),
	FOREIGN KEY (role_id) references roles (id_roles)
);

INSERT INTO roles (name)
VALUES ('ROLE_MANAGER'),
       ('ROLE_LEADER'),
       ('ROLE_ADMIN');

INSERT INTO users (username, password, manager)
VALUES ('manager', '$2a$10$AL3BxKTjXlbu7.9NA5hRPutAw752Mo3pA8QnZV7FnGbxOxyHAuqcG', 'Иванов'),
       ('leader', '$2a$10$AL3BxKTjXlbu7.9NA5hRPutAw752Mo3pA8QnZV7FnGbxOxyHAuqcG', 'Петров'),
       ('admin', '$2a$10$AL3BxKTjXlbu7.9NA5hRPutAw752Mo3pA8QnZV7FnGbxOxyHAuqcG', 'Сидоров');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (3, 1),
       (3, 2),
       (3, 3);