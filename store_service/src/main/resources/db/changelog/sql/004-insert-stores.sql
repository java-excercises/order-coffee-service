-- db/changelog/sql/004-insert-stores.sql

-- Liquibase formatted SQL

-- ChangeSet
-- ChangeSet author: Trung Doan
-- ChangeSet id: 004
INSERT INTO public.coffee_store(
	id, max_no_queues, menu_items, name, store_location)
	VALUES (1, 3, '[ {"product": { "id": 1, "name": "Product 01", "originalPrice": 10.5},'
	'"sellingPrice": 10.0, "quantity": 100}, {"product": { "id": 2, "name": "Product 02", "originalPrice": 2.6}, '
	'"sellingPrice": 2.7, "quantity": 200}, {"product": { "id": 3, "name": "Product 03", "originalPrice": 15.0}, '
    '"sellingPrice": 14.0, "quantity": 50}]', 'Store 01', 'HCM City - VN');

INSERT INTO public.coffee_store(
	id, max_no_queues, menu_items, name, store_location)
	VALUES (2, 2, '[ {"product": { "id": 1, "name": "Product 01", "originalPrice": 10.5},'
    	'"sellingPrice": 10.0, "quantity": 100}, {"product": { "id": 2, "name": "Product 02", "originalPrice": 2.6}, '
    	'"sellingPrice": 2.7, "quantity": 200}, {"product": { "id": 3, "name": "Product 03", "originalPrice": 15.0}, '
        '"sellingPrice": 14.0, "quantity": 50}]', 'Store 02', 'HCM City - VN');

INSERT INTO public.coffee_store(
	id, max_no_queues, menu_items, name, store_location)
	VALUES (3, 2, '[ {"product": { "id": 1, "name": "Product 01", "originalPrice": 10.5},'
    	'"sellingPrice": 10.0, "quantity": 100}, {"product": { "id": 2, "name": "Product 02", "originalPrice": 2.6}, '
    	'"sellingPrice": 2.7, "quantity": 200}, {"product": { "id": 3, "name": "Product 03", "originalPrice": 15.0}, '
        '"sellingPrice": 14.0, "quantity": 50}]', 'Store 03', 'HCM City - VN');
-- /ChangeSet