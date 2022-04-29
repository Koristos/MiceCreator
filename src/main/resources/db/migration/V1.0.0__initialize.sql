CREATE TABLE countries
(
	id_countries INT PRIMARY KEY AUTO_INCREMENT,
	country_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO countries (country_name)
VALUES ('РОССИЯ'),
       ('ТУРЦИЯ'),
       ('ИСПАНИЯ');

CREATE TABLE acc_types
(
	id_acc_types  INT PRIMARY KEY AUTO_INCREMENT,
	acc_type_name VARCHAR(15) NOT NULL UNIQUE,
	acc_number    INT         NOT NULL
);

INSERT INTO acc_types (acc_type_name, acc_number)
VALUES ('SGL', 1),
       ('DBL', 2),
       ('TPL', 3);

CREATE TABLE regions
(
	id_regions  INT PRIMARY KEY AUTO_INCREMENT,
	country_id  INT         NOT NULL,
	region_name VARCHAR(50) NOT NULL UNIQUE
);

ALTER TABLE regions
	ADD FOREIGN KEY (country_id)
		REFERENCES countries (id_countries);

INSERT INTO regions (country_id, region_name)
VALUES (1, 'МОСКВА'),
       (1, 'КРАСНОДАРСКИЙ КРАЙ'),
       (2, 'АНТАЛИЙСКАЯ РИВЬЕРА'),
       (2, 'СТАМБУЛ'),
       (3, 'КАТАЛОНИЯ'),
       (3, 'МАЙОРКА');

CREATE TABLE airports
(
	id_airports  INT PRIMARY KEY AUTO_INCREMENT,
	region_id    INT         NOT NULL,
	airport_code VARCHAR(3)  NOT NULL UNIQUE,
	airport_name VARCHAR(50) NOT NULL UNIQUE
);

ALTER TABLE airports
	ADD FOREIGN KEY (region_id)
		REFERENCES regions (id_regions);

INSERT INTO airports (region_id, airport_code, airport_name)
VALUES (1, 'SVO', 'ШЕРЕМЕТЬЕВО'),
       (1, 'DME', 'ДОМОДЕДОВО'),
       (2, 'AER', 'АДЛЕР'),
       (2, 'AAQ', 'АНАПА'),
       (3, 'AYT', 'АНТАЛИЯ'),
       (3, 'GZP', 'ГАЗИПАША'),
       (4, 'IST', 'СТАМБУЛ'),
       (4, 'SAW', 'САБИХА-ГЁКЧЕН'),
       (5, 'BCN', 'БАРСЕЛОНА'),
       (6, 'PMI', 'ПАЛЬМА-ДЕ-МАЙОРКА');

CREATE TABLE locations
(
	id_locations  INT PRIMARY KEY AUTO_INCREMENT,
	region_id     INT           NOT NULL,
	location_name VARCHAR(50)   NOT NULL UNIQUE,
	location_desc VARCHAR(1000) NOT NULL
);

ALTER TABLE locations
	ADD FOREIGN KEY (region_id)
		REFERENCES regions (id_regions);

INSERT INTO locations (region_id, location_name, location_desc)
VALUES (1, 'ГОРОД МОСКВА', 'Описание Москвы не готово'),
       (1, 'МОСКОВСКАЯ ОБЛАСТЬ', 'Описание Московской области не готово'),
       (2, 'СОЧИ', 'Описание Сочи не готово'),
       (2, 'АНАПА', 'Описание Анапы не готово'),
       (3, 'АНТАЛИЯ', 'Описание Анталии не готово'),
       (3, 'БЕЛЕК', 'Описание Белека не готово'),
       (4, 'ГОРОД СТАМБУЛ', 'Описание Стамбула не готово'),
       (5, 'САЛОУ', 'Описание Салоу не готово'),
       (5, 'ЛОРЕТ-ДЕ-МАР', 'Описание Лорет-де-Мар не готово'),
       (6, 'МАЛЬМА НОВА', 'Описание Пальма Нова не готово'),
       (6, 'МАГАЛУФ', 'Описание Магалуфа не готово');

CREATE TABLE hotels
(
	id_hotels   INT PRIMARY KEY AUTO_INCREMENT,
	location_id INT           NOT NULL,
	hotel_name  VARCHAR(50)   NOT NULL UNIQUE,
	hotel_desc  VARCHAR(1000) NOT NULL
);

ALTER TABLE hotels
	ADD FOREIGN KEY (location_id)
		REFERENCES locations (id_locations);

INSERT INTO hotels (location_id, hotel_name, hotel_desc)
VALUES (1, 'NOVOTEL MOSCOW', 'Описание отеля не готово'),
       (1, 'RADISSON SLAVYANSKAYA', 'Описание отеля не готово'),
       (2, 'FOREST', 'Описание отеля не готово'),
       (2, 'ARTHURS SPA HOTEL', 'Описание отеля не готово'),
       (3, 'PROMETEY CLUB', 'Описание отеля не готово'),
       (3, 'BOGATYR', 'Описание отеля не готово'),
       (4, 'LAZURNIY BEREG', 'Описание отеля не готово'),
       (4, 'CORUDO FAMILY RESORT AND SPA', 'Описание отеля не готово'),
       (5, 'BAHIA LARA', 'Описание отеля не готово'),
       (5, 'KREMLIN PALACE', 'Описание отеля не готово'),
       (6, 'GLORIA VERDE', 'Описание отеля не готово'),
       (6, 'BELCONTI', 'Описание отеля не готово'),
       (7, 'PRESIDENT', 'Описание отеля не готово'),
       (7, 'DIVAN ISTANBUL CITY', 'Описание отеля не готово'),
       (8, 'BEST DA VINCI', 'Описание отеля не готово'),
       (8, 'H10 VINTAGE SALOU', 'Описание отеля не готово'),
       (9, 'ROSAMAR&SPA', 'Описание отеля не готово'),
       (9, 'DON JUAN LLORET', 'Описание отеля не готово'),
       (10, 'IBERSOL SON CALIU MAR', 'Описание отеля не готово'),
       (10, 'FERGUS STYLE PALMANOVA', 'Описание отеля не готово'),
       (11, 'SOL BARBADOS', 'Описание отеля не готово'),
       (11, 'MELIA CALVIA BEACH', 'Описание отеля не готово');

CREATE TABLE rooms
(
	id_rooms  INT PRIMARY KEY AUTO_INCREMENT,
	hotel_id  INT           NOT NULL,
	room_name VARCHAR(50)   NOT NULL,
	room_desc VARCHAR(1000) NOT NULL
);

ALTER TABLE rooms
	ADD FOREIGN KEY (hotel_id)
		REFERENCES hotels (id_hotels);

INSERT INTO rooms (hotel_id, room_name, room_desc)
VALUES (1, 'STANDARD', 'Описание не готово'),
       (2, 'STANDARD', 'Описание не готово'),
       (3, 'STANDARD', 'Описание не готово'),
       (4, 'STANDARD', 'Описание не готово'),
       (5, 'STANDARD', 'Описание не готово'),
       (6, 'STANDARD', 'Описание не готово'),
       (7, 'STANDARD', 'Описание не готово'),
       (8, 'STANDARD', 'Описание не готово'),
       (9, 'STANDARD', 'Описание не готово'),
       (10, 'STANDARD', 'Описание не готово'),
       (11, 'STANDARD', 'Описание не готово'),
       (12, 'STANDARD', 'Описание не готово'),
       (13, 'STANDARD', 'Описание не готово'),
       (14, 'STANDARD', 'Описание не готово'),
       (15, 'STANDARD', 'Описание не готово'),
       (16, 'STANDARD', 'Описание не готово'),
       (17, 'STANDARD', 'Описание не готово'),
       (18, 'STANDARD', 'Описание не готово'),
       (19, 'STANDARD', 'Описание не готово'),
       (20, 'STANDARD', 'Описание не готово'),
       (21, 'STANDARD', 'Описание не готово'),
       (22, 'STANDARD', 'Описание не готово'),
       (1, 'SUPERIOR', 'Описание не готово'),
       (3, 'SUPERIOR', 'Описание не готово'),
       (5, 'SUPERIOR', 'Описание не готово'),
       (7, 'SUPERIOR', 'Описание не готово'),
       (9, 'SUPERIOR', 'Описание не готово'),
       (11, 'SUPERIOR', 'Описание не готово'),
       (13, 'SUPERIOR', 'Описание не готово'),
       (15, 'SUPERIOR', 'Описание не готово'),
       (17, 'SUPERIOR', 'Описание не готово'),
       (19, 'SUPERIOR', 'Описание не готово'),
       (21, 'SUPERIOR', 'Описание не готово');

CREATE TABLE airlines
(
	id_airlines  INT PRIMARY KEY AUTO_INCREMENT,
	airline_name VARCHAR(50)   NOT NULL UNIQUE,
	airline_desc VARCHAR(1000) NOT NULL
);

INSERT INTO airlines (airline_name, airline_desc)
VALUES ('AEROFLOT', 'Описание не готово'),
       ('AZUR AIR', 'Описание не готово'),
       ('IBERIA', 'Описание не готово');

CREATE TABLE region_services
(
	id_region_services  INT PRIMARY KEY AUTO_INCREMENT,
	region_id           INT           NOT NULL,
	region_service_name VARCHAR(50)   NOT NULL UNIQUE,
	region_service_desc VARCHAR(1000) NOT NULL
);

ALTER TABLE region_services
	ADD FOREIGN KEY (region_id)
		REFERENCES regions (id_regions);

INSERT INTO region_services (region_id, region_service_name, region_service_desc)
VALUES (1, 'ОБЗОРНАЯ ЭКСКУРСИЯ ПО МОСКВЕ', 'Описание не готово'),
       (2, 'ЭКСКУРСИЯ НА КРАСНУЮ ПОЛЯНУ', 'Описание не готово'),
       (3, 'ПИРАТСКАЯ ЯХТА', 'Описание не готово'),
       (4, 'ОБЗОРНАЯ ЭКСКУРСИЯ ПО СТАМБУЛУ', 'Описание не готово'),
       (5, 'ЭКСКУРСИЯ В БАРСЕЛОНУ', 'Описание не готово');

CREATE TABLE hotel_services
(
	id_hotel_services  INT PRIMARY KEY AUTO_INCREMENT,
	hotel_id           INT           NOT NULL,
	hotel_service_name VARCHAR(50)   NOT NULL,
	hotel_service_desc VARCHAR(1000) NOT NULL
);

ALTER TABLE hotel_services
	ADD FOREIGN KEY (hotel_id)
		REFERENCES hotels (id_hotels);

INSERT INTO hotel_services (hotel_id, hotel_service_name, hotel_service_desc)
VALUES (5, 'БАНКЕТ', 'Описание не готово'),
       (6, 'БАНКЕТ', 'Описание не готово'),
       (7, 'БАНКЕТ', 'Описание не готово'),
       (8, 'БАНКЕТ', 'Описание не готово'),
       (9, 'БАНКЕТ', 'Описание не готово'),
       (10, 'БАНКЕТ', 'Описание не готово'),
       (11, 'БАНКЕТ', 'Описание не готово'),
       (12, 'БАНКЕТ', 'Описание не готово'),
       (13, 'БАНКЕТ', 'Описание не готово'),
       (14, 'БАНКЕТ', 'Описание не готово'),
       (15, 'БАНКЕТ', 'Описание не готово'),
       (16, 'БАНКЕТ', 'Описание не готово'),
       (17, 'БАНКЕТ', 'Описание не готово'),
       (18, 'БАНКЕТ', 'Описание не готово'),
       (19, 'БАНКЕТ', 'Описание не готово'),
       (20, 'БАНКЕТ', 'Описание не готово'),
       (21, 'БАНКЕТ', 'Описание не готово'),
       (22, 'БАНКЕТ', 'Описание не готово');

CREATE TABLE tours
(
	id_tours    INT PRIMARY KEY AUTO_INCREMENT,
	country_id  INT            NOT NULL,
	pax         INT            NOT NULL,
	start_date  DATE           NOT NULL,
	end_date    DATE           NOT NULL,
	total_price DECIMAL(20, 2) NOT NULL
);

ALTER TABLE tours
	ADD FOREIGN KEY (country_id)
		REFERENCES countries (id_countries);

CREATE TABLE accommodations
(
	id_accommodations INT PRIMARY KEY AUTO_INCREMENT,
	tour_id           INT           NOT NULL,
	room_id           INT           NOT NULL,
	acc_type_id       INT           NOT NULL,
	check_in_date     DATE          NOT NULL,
	check_out_date    DATE          NOT NULL,
	pax               INT           NOT NULL,
	price             DECIMAL(9, 2) NOT NULL
);

ALTER TABLE accommodations
	ADD FOREIGN KEY (tour_id)
		REFERENCES tours (id_tours),
	ADD FOREIGN KEY (room_id)
		REFERENCES rooms (id_rooms),
	ADD FOREIGN KEY (acc_type_id)
		REFERENCES acc_types (id_acc_types);

CREATE TABLE flights
(
	id_flights           INT PRIMARY KEY AUTO_INCREMENT,
	tour_id              INT           NOT NULL,
	airline_id           INT           NOT NULL,
	departure_airport_id INT           NOT NULL,
	arrival_airport_id   INT           NOT NULL,
	departure_date       DATE          NOT NULL,
	arrival_date         DATE          NOT NULL,
	pax                  INT           NOT NULL,
	price                DECIMAL(9, 2) NOT NULL
);

ALTER TABLE flights
	ADD FOREIGN KEY (tour_id)
		REFERENCES tours (id_tours),
	ADD FOREIGN KEY (airline_id)
		REFERENCES airlines (id_airlines),
	ADD FOREIGN KEY (departure_airport_id)
		REFERENCES airports (id_airports),
	ADD FOREIGN KEY (arrival_airport_id)
		REFERENCES airports (id_airports);

CREATE TABLE region_events
(
	id_region_events  INT PRIMARY KEY AUTO_INCREMENT,
	tour_id           INT           NOT NULL,
	region_service_id INT           NOT NULL,
	region_event_date DATE          NOT NULL,
	pax               INT           NOT NULL,
	price             DECIMAL(9, 2) NOT NULL
);

ALTER TABLE region_events
	ADD FOREIGN KEY (tour_id)
		REFERENCES tours (id_tours),
	ADD FOREIGN KEY (region_service_id)
		REFERENCES region_services (id_region_services);

CREATE TABLE hotel_events
(
	id_hotel_events   INT PRIMARY KEY AUTO_INCREMENT,
	tour_id           INT           NOT NULL,
	hotel_service_id  INT           NOT NULL,
	hotel_events_date DATE          NOT NULL,
	pax               INT           NOT NULL,
	price             DECIMAL(9, 2) NOT NULL
);

ALTER TABLE hotel_events
	ADD FOREIGN KEY (tour_id)
		REFERENCES tours (id_tours),
	ADD FOREIGN KEY (hotel_service_id)
		REFERENCES hotel_services (id_hotel_services);





