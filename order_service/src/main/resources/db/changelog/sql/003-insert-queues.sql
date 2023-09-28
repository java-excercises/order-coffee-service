-- db/changelog/sql/003-insert-queues.sql

-- Liquibase formatted SQL

-- ChangeSet
-- ChangeSet author: Trung Doan
-- ChangeSet id: 003

INSERT INTO public.order_queue(id, store_id, queue_index) VALUES (1, 1, 1);
INSERT INTO public.order_queue(id, store_id, queue_index) VALUES (2, 1, 2);
INSERT INTO public.order_queue(id, store_id, queue_index) VALUES (3, 1, 3);
INSERT INTO public.order_queue(id, store_id, queue_index) VALUES (4, 2, 1);
INSERT INTO public.order_queue(id, store_id, queue_index) VALUES (5, 2, 2);
INSERT INTO public.order_queue(id, store_id, queue_index) VALUES (6, 3, 1);
INSERT INTO public.order_queue(id, store_id, queue_index) VALUES (7, 3, 2);
-- /ChangeSet