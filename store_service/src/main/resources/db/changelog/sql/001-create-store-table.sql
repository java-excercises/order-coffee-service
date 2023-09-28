-- db/changelog/sql/001-create-store-table.sql

-- Liquibase formatted SQL

-- ChangeSet
-- ChangeSet author: Trung Doan
-- ChangeSet id: 001
CREATE TABLE IF NOT EXISTS public.coffee_store
(
    id bigint NOT NULL,
    max_no_queues character varying(255),
    menu_items jsonb,
    name character varying(255),
    store_location character varying(255),
    CONSTRAINT coffee_store_pkey PRIMARY KEY (id)
)
-- /ChangeSet