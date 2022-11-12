ALTER TABLE accommodations ADD COLUMN netto_price DECIMAL(9, 2);

UPDATE accommodations SET netto_price = 0 WHERE netto_price IS NULL;

ALTER TABLE accommodations ADD COLUMN creation_date DATE;

UPDATE accommodations SET creation_date = '2022-06-20' WHERE creation_date IS NULL;



ALTER TABLE flights ADD COLUMN netto_price DECIMAL(9, 2);

UPDATE flights SET netto_price = 0 WHERE netto_price IS NULL;

ALTER TABLE flights ADD COLUMN creation_date DATE;

UPDATE flights SET creation_date = '2022-06-20' WHERE creation_date IS NULL;



ALTER TABLE hotel_events ADD COLUMN netto_price DECIMAL(9, 2);

UPDATE hotel_events SET netto_price = 0 WHERE netto_price IS NULL;

ALTER TABLE hotel_events ADD COLUMN creation_date DATE;

UPDATE hotel_events SET creation_date = '2022-06-20' WHERE creation_date IS NULL;



ALTER TABLE region_events ADD COLUMN netto_price DECIMAL(9, 2);

UPDATE region_events SET netto_price = 0 WHERE netto_price IS NULL;

ALTER TABLE region_events ADD COLUMN creation_date DATE;

UPDATE region_events SET creation_date = '2022-06-20' WHERE creation_date IS NULL;



ALTER TABLE tours ADD COLUMN netto_total DECIMAL(9, 2);

UPDATE tours SET netto_total = 0 WHERE netto_total IS NULL;

ALTER TABLE tours ADD COLUMN creation_date DATE;

UPDATE tours SET creation_date = '2022-06-20' WHERE creation_date IS NULL;

ALTER TABLE tours ADD COLUMN user_id INT;

UPDATE tours SET user_id = 1 WHERE user_id IS NULL;

ALTER TABLE users ADD CONSTRAINT uq_manager UNIQUE (manager);
