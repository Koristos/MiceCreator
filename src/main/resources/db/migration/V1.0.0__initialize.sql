CREATE TABLE countries
(
	id_countries INT PRIMARY KEY AUTO_INCREMENT,
	country_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO countries (country_name)
VALUES ('Россия'),
       ('Турция'),
       ('Испания');

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
	region_name VARCHAR(45) NOT NULL UNIQUE
);

ALTER TABLE regions
	ADD FOREIGN KEY (country_id)
		REFERENCES countries (id_countries);

INSERT INTO regions (country_id, region_name)
VALUES (1, 'Москва'),
       (1, 'Краснодарский край'),
       (2, 'Анталийская ривьера'),
       (2, 'Стамбул'),
       (3, 'Каталония'),
       (3, 'Майорка');

CREATE TABLE airports
(
	id_airports  INT PRIMARY KEY AUTO_INCREMENT,
	region_id    INT         NOT NULL,
	airport_code VARCHAR(3)  NOT NULL UNIQUE,
	airport_name VARCHAR(25) NOT NULL UNIQUE
);

ALTER TABLE airports
	ADD FOREIGN KEY (region_id)
		REFERENCES regions (id_regions);

INSERT INTO airports (region_id, airport_code, airport_name)
VALUES (1, 'SVO', 'Шереметьево'),
       (1, 'DME', 'Домодедово'),
       (2, 'AER', 'Адлер'),
       (2, 'AAQ', 'Анапа'),
       (3, 'AYT', 'Анталия'),
       (3, 'GZP', 'Газипаша'),
       (4, 'IST', 'Стамбул'),
       (4, 'SAW', 'Сабиха-Гёкчен'),
       (5, 'BCN', 'Барселона'),
       (6, 'PMI', 'Пальма-де-Майорка');

CREATE TABLE locations
(
	id_locations  INT PRIMARY KEY AUTO_INCREMENT,
	region_id     INT           NOT NULL,
	location_name VARCHAR(25)   NOT NULL UNIQUE,
	location_desc VARCHAR(1000) NOT NULL
);

ALTER TABLE locations
	ADD FOREIGN KEY (region_id)
		REFERENCES regions (id_regions);

INSERT INTO locations (region_id, location_name, location_desc)
VALUES (1, 'город Москва', 'Описание Москвы не готово'),
       (1, 'Московская область', 'Описание Московской области не готово'),
       (2, 'Сочи', 'Описание Сочи не готово'),
       (2, 'Анапа', 'Описание Анапы не готово'),
       (3, 'Анталия', 'Описание Анталии не готово'),
       (3, 'Белек', 'Описание Белека не готово'),
       (4, 'город Стамбул', 'Описание Стамбула не готово'),
       (5, 'Салоу', 'Описание Салоу не готово'),
       (5, 'Лорет-де-Мар', 'Описание Лорет-де-Мар не готово'),
       (6, 'Пальма Нова', 'Описание Пальма Нова не готово'),
       (6, 'Магалуф', 'Описание Магалуфа не готово');

CREATE TABLE hotels
(
	id_hotels   INT PRIMARY KEY AUTO_INCREMENT,
	location_id INT           NOT NULL,
	hotel_name  VARCHAR(25)   NOT NULL UNIQUE,
	hotel_desc  VARCHAR(1000) NOT NULL
);

ALTER TABLE hotels
	ADD FOREIGN KEY (location_id)
		REFERENCES locations (id_locations);

INSERT INTO hotels (location_id, hotel_name, hotel_desc)
VALUES (1, 'Novotel Moscow', 'Описание отеля не готово'),
       (1, 'Radisson Slavyanskaya', 'Описание отеля не готово'),
       (2, 'Forest', 'Описание отеля не готово'),
       (2, 'Arthurs SPA Hotel', 'Описание отеля не готово'),
       (3, 'Prometey Club', 'Описание отеля не готово'),
       (3, 'Bogatyr', 'Описание отеля не готово'),
       (4, 'Lazurniy Bereg', 'Описание отеля не готово'),
       (4, 'Corudo Family Resort&Spa', 'Описание отеля не готово'),
       (5, 'Bahia Lara', 'Описание отеля не готово'),
       (5, 'Kremlin Palace', 'Описание отеля не готово'),
       (6, 'Gloria Verde', 'Описание отеля не готово'),
       (6, 'Belconti', 'Описание отеля не готово'),
       (7, 'President', 'Описание отеля не готово'),
       (7, 'Divan Istanbul City', 'Описание отеля не готово'),
       (8, 'Best Da Vinci', 'Описание отеля не готово'),
       (8, 'H10 Vintage Salou', 'Описание отеля не готово'),
       (9, 'Rosamar&Spa', 'Описание отеля не готово'),
       (9, 'Don Juan Lloret', 'Описание отеля не готово'),
       (10, 'Ibersol Son Caliu Mar', 'Описание отеля не готово'),
       (10, 'FERGUS Style Palmanova', 'Описание отеля не готово'),
       (11, 'Sol Barbados', 'Описание отеля не готово'),
       (11, 'Melia Calvia Beach', 'Описание отеля не готово');

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
VALUES (1, 'standard', 'Описание не готово'),
       (2, 'standard', 'Описание не готово'),
       (3, 'standard', 'Описание не готово'),
       (4, 'standard', 'Описание не готово'),
       (5, 'standard', 'Описание не готово'),
       (6, 'standard', 'Описание не готово'),
       (7, 'standard', 'Описание не готово'),
       (8, 'standard', 'Описание не готово'),
       (9, 'standard', 'Описание не готово'),
       (10, 'standard', 'Описание не готово'),
       (11, 'standard', 'Описание не готово'),
       (12, 'standard', 'Описание не готово'),
       (13, 'standard', 'Описание не готово'),
       (14, 'standard', 'Описание не готово'),
       (15, 'standard', 'Описание не готово'),
       (16, 'standard', 'Описание не готово'),
       (17, 'standard', 'Описание не готово'),
       (18, 'standard', 'Описание не готово'),
       (19, 'standard', 'Описание не готово'),
       (20, 'standard', 'Описание не готово'),
       (21, 'standard', 'Описание не готово'),
       (22, 'standard', 'Описание не готово'),
       (1, 'superior', 'Описание не готово'),
       (3, 'superior', 'Описание не готово'),
       (5, 'superior', 'Описание не готово'),
       (7, 'superior', 'Описание не готово'),
       (9, 'superior', 'Описание не готово'),
       (11, 'superior', 'Описание не готово'),
       (13, 'superior', 'Описание не готово'),
       (15, 'superior', 'Описание не готово'),
       (17, 'superior', 'Описание не готово'),
       (19, 'superior', 'Описание не готово'),
       (21, 'superior', 'Описание не готово');

CREATE TABLE airlines
(
	id_airlines  INT PRIMARY KEY AUTO_INCREMENT,
	airline_name VARCHAR(45)   NOT NULL UNIQUE,
	airline_desc VARCHAR(1000) NOT NULL
);

INSERT INTO airlines (airline_name, airline_desc)
VALUES ('Aeroflot', 'Описание не готово'),
       ('Azur Air', 'Описание не готово'),
       ('Iberia', 'Описание не готово');

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
VALUES (1, 'Обзорная экскурсия по Москве', 'Описание не готово'),
       (2, 'Экскурсия на Красную Поляну', 'Описание не готово'),
       (3, 'Пиратская Яхта', 'Описание не готово'),
       (4, 'Обзорная экскурсия по Стамбулу', 'Описание не готово'),
       (5, 'Экскурсия в Барселону', 'Описание не готово');

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
VALUES (5, 'банкет', 'Описание не готово'),
       (6, 'банкет', 'Описание не готово'),
       (7, 'банкет', 'Описание не готово'),
       (8, 'банкет', 'Описание не готово'),
       (9, 'банкет', 'Описание не готово'),
       (10, 'банкет', 'Описание не готово'),
       (11, 'банкет', 'Описание не готово'),
       (12, 'банкет', 'Описание не готово'),
       (13, 'банкет', 'Описание не готово'),
       (14, 'банкет', 'Описание не готово'),
       (15, 'банкет', 'Описание не готово'),
       (16, 'банкет', 'Описание не готово'),
       (17, 'банкет', 'Описание не готово'),
       (18, 'банкет', 'Описание не готово'),
       (19, 'банкет', 'Описание не готово'),
       (20, 'банкет', 'Описание не готово'),
       (21, 'банкет', 'Описание не готово'),
       (22, 'банкет', 'Описание не готово');

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





