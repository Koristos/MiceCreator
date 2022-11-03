CREATE TABLE currency
(
	id_currency         INT PRIMARY KEY AUTO_INCREMENT,
	currency_name       VARCHAR(3) NOT NULL
);

INSERT INTO currency (id_currency, currency_name)
VALUES (1, 'RUB'),
       (2, 'USD'),
       (3, 'EUR');

CREATE TABLE course
(
	id_course         INT PRIMARY KEY AUTO_INCREMENT,
	course_date       DATE NOT NULL,
	course_rate       DECIMAL(9, 2) NOT NULL,
	currency_id       INT NOT NULL
);

ALTER TABLE course
	ADD FOREIGN KEY (currency_id)
		REFERENCES currency (id_currency);

ALTER TABLE countries ADD COLUMN currency_id INT;

UPDATE countries SET currency_id = 2 WHERE currency_id IS NULL;

ALTER TABLE countries
	ADD FOREIGN KEY (currency_id)
		REFERENCES currency (id_currency);