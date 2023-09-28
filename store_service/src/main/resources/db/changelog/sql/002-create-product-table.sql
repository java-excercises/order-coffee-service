-- db/changelog/sql/002-create-product-table.sql

-- Liquibase formatted SQL

-- ChangeSet
-- ChangeSet author: Trung Doan
-- ChangeSet id: 002
CREATE TABLE IF NOT EXISTS public.product
(
    id bigint NOT NULL,
    name character varying(255),
    original_price double precision,
    CONSTRAINT product_pkey PRIMARY KEY (id)
)
-- /ChangeSet