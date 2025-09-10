-- V2__remove_creation_date_from_products.sql
-- Remove a coluna 'creation_date' da tabela 'products', pois ela era redundante.
-- A funcionalidade de data de criação agora é coberta exclusivamente pelo campo
-- 'created_at', herdado da entidade base, garantindo consistência na auditoria.

ALTER TABLE products
DROP COLUMN IF EXISTS creation_date;