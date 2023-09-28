-- db/changelog/sql/003-insert-products.sql

-- Liquibase formatted SQL

-- ChangeSet
-- ChangeSet author: Trung Doan
-- ChangeSet id: 003
INSERT INTO public.product(id, name, original_price) VALUES (1, 'Product 01', 10.5);
INSERT INTO public.product(id, name, original_price) VALUES (2, 'Product 02', 2.6);
INSERT INTO public.product(id, name, original_price) VALUES (3, 'Product 03', 15.0);
-- /ChangeSet