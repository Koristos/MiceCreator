CREATE TABLE hotel_images
(
	id_hotel_images INT PRIMARY KEY AUTO_INCREMENT,
	hotel_id        INT NOT NULL,
	image           MEDIUMBLOB
);

ALTER TABLE hotel_images
	ADD FOREIGN KEY (hotel_id)
		REFERENCES hotels (id_hotels);

CREATE TABLE hotel_service_images
(
	id_hotel_service_images INT PRIMARY KEY AUTO_INCREMENT,
	hotel_service_id        INT NOT NULL,
	image                   MEDIUMBLOB
);

ALTER TABLE hotel_service_images
	ADD FOREIGN KEY (hotel_service_id)
		REFERENCES hotel_services (id_hotel_services);

CREATE TABLE region_service_images
(
	id_region_service_images INT PRIMARY KEY AUTO_INCREMENT,
	region_service_id        INT NOT NULL,
	image                    MEDIUMBLOB
);

ALTER TABLE region_service_images
	ADD FOREIGN KEY (region_service_id)
		REFERENCES region_services (id_region_services);

CREATE TABLE room_images
(
	id_room_images INT PRIMARY KEY AUTO_INCREMENT,
	room_id        INT NOT NULL,
	image          MEDIUMBLOB
);

ALTER TABLE room_images
	ADD FOREIGN KEY (room_id)
		REFERENCES rooms (id_rooms);

CREATE TABLE region_images
(
	id_region_images INT PRIMARY KEY AUTO_INCREMENT,
	region_id        INT NOT NULL,
	image            MEDIUMBLOB
);

ALTER TABLE region_images
	ADD FOREIGN KEY (region_id)
		REFERENCES regions (id_regions);

CREATE TABLE location_images
(
	id_location_images INT PRIMARY KEY AUTO_INCREMENT,
	location_id        INT NOT NULL,
	image              MEDIUMBLOB
);

ALTER TABLE location_images
	ADD FOREIGN KEY (location_id)
		REFERENCES locations (id_locations);