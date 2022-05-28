DROP TABLE hotel_images;
DROP TABLE hotel_service_images;
DROP TABLE region_service_images;
DROP TABLE room_images;
DROP TABLE region_images;
DROP TABLE location_images;

ALTER TABLE hotels ADD COLUMN image_one VARCHAR(50);
ALTER TABLE hotels ADD COLUMN image_two VARCHAR(50);

ALTER TABLE hotel_services ADD COLUMN image_one VARCHAR(50);
ALTER TABLE hotel_services ADD COLUMN image_two VARCHAR(50);

ALTER TABLE region_services ADD COLUMN image_one VARCHAR(50);
ALTER TABLE region_services ADD COLUMN image_two VARCHAR(50);

ALTER TABLE rooms ADD COLUMN image_one VARCHAR(50);
ALTER TABLE rooms ADD COLUMN image_two VARCHAR(50);

ALTER TABLE regions ADD COLUMN image_one VARCHAR(50);

ALTER TABLE locations ADD COLUMN image_one VARCHAR(50);
