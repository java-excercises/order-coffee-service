-- db/changelog/sql/002-create-queue-table.sql

-- Liquibase formatted SQL

-- ChangeSet
-- ChangeSet author: Trung Doan
-- ChangeSet id: 002
CREATE TABLE IF NOT EXISTS public.order_queue
(
    id bigint NOT NULL,
    store_id bigint NOT NULL,
    queue_index integer NOT NULL,
    having_order_processing boolean NOT NULL DEFAULT false,
    CONSTRAINT order_queue_pkey PRIMARY KEY (id)
)
-- /ChangeSet