-- db/changelog/sql/004-insert-orders.sql

-- Liquibase formatted SQL

-- ChangeSet
-- ChangeSet author: Trung Doan
-- ChangeSet id: 003

INSERT INTO public.coffee_order(
	id, items, order_status, sub_total, total, vat, store_id, queue_id)
	VALUES (1, '['
	'{"id": "1", "sellingPrice": 10.0, "quantity": 5, "product": {"id": 1, "name": "Product 01", "originalPrice": 10.5}},'
	'{"id": "2", "sellingPrice": 14.0, "quantity": 5, "product": {"id": 3, "name": "Product 03", "originalPrice": 15.0}}'
	']', 'ORDERED', 120, 132, 12, 1, 1);

INSERT INTO public.coffee_order(
	id, items, order_status, sub_total, total, vat, store_id, queue_id)
	VALUES (2, '['
	'{"id": "3", "sellingPrice": 10.0, "quantity": 5, "product": {"id": 1, "name": "Product 01", "originalPrice": 10.5}},'
	'{"id": "4", "sellingPrice": 3.0, "quantity": 5, "product": {"id": 2, "name": "Product 02", "originalPrice": 2.6}}'
	']', 'ORDERED', 65, 71.5, 6.5, 1, 2);

INSERT INTO public.coffee_order(
	id, items, order_status, sub_total, total, vat, store_id, queue_id)
	VALUES (3, '['
	'{"id": "2", "sellingPrice": 14.0, "quantity": 5, "product": {"id": 3, "name": "Product 03", "originalPrice": 15.0}},'
	'{"id": "4", "sellingPrice": 3.0, "quantity": 5, "product": {"id": 2, "name": "Product 02", "originalPrice": 2.6}}'
	']', 'ORDERED', 85, 93.5, 8.5, 1, 3);
-- /ChangeSet