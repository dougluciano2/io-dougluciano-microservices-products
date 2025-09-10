CREATE TABLE products (
    -- Herdado de AbstractEntity
    id BIGSERIAL PRIMARY KEY,

    -- Campos de Product
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2) NOT NULL,
    sku VARCHAR(100) NOT NULL UNIQUE,

    -- Herdado de AbstractFullEntity
    created_at TIMESTAMPTZ NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by VARCHAR(255)
);

-- Adiciona um índice na coluna SKU para otimizar as buscas
CREATE INDEX idx_products_sku ON products(sku);