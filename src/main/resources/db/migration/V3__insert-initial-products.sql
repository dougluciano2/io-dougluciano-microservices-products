-- V3__insert_initial_products.sql
-- Insere uma massa de dados inicial com 20 produtos para facilitar o desenvolvimento e testes.

INSERT INTO products (id, name, description, price, sku, created_at, created_by, updated_at) VALUES
(1, 'Notebook Gamer Pro', 'Notebook com placa de vídeo dedicada RTX 4070, 32GB RAM e SSD de 1TB.', 9500.00, 'NTB-GMR-001', NOW(), 'testuser', NOW()),
(2, 'Monitor Ultrawide 34" QHD', 'Monitor Curvo Ultrawide com resolução QHD (3440x1440) e 144Hz.', 3200.50, 'MON-UW-001', NOW(), 'testuser', NOW()),
(3, 'Teclado Mecânico RGB TKL', 'Teclado mecânico Tenkeyless com switches blue e iluminação RGB.', 450.75, 'TEC-MEC-001', NOW(), 'testuser', NOW()),
(4, 'Mouse Sem Fio Gamer Leve', 'Mouse com sensor de alta precisão, 6 botões e bateria de longa duração.', 350.00, 'MSE-GMR-001', NOW(), 'testuser', NOW()),
(5, 'SSD NVMe PCIe 4.0 2TB', 'Unidade de estado sólido com velocidades de leitura/escrita de até 7000 MB/s.', 1200.00, 'SSD-NVME-001', NOW(), 'testuser', NOW()),
(6, 'Headset Gamer 7.1 Surround', 'Headset com som surround 7.1, microfone com cancelamento de ruído.', 550.00, 'HDS-GMR-001', NOW(), 'testuser', NOW()),
(7, 'Cadeira Gamer Ergonômica', 'Cadeira ergonômica com suporte lombar e braços ajustáveis.', 1800.00, 'CAD-GMR-001', NOW(), 'testuser', NOW()),
(8, 'Processador Core i9 14th Gen', 'Processador de última geração com 24 núcleos e 32 threads.', 4500.00, 'CPU-I9-14G', NOW(), 'testuser', NOW()),
(9, 'Placa de Vídeo RTX 4080 Super', 'Placa de vídeo com 16GB de memória GDDR6X.', 8500.00, 'GPU-4080-S', NOW(), 'testuser', NOW()),
(10, 'Memória RAM DDR5 32GB Kit', 'Kit com 2 módulos de 16GB DDR5 6000MHz.', 950.00, 'RAM-DDR5-32K', NOW(), 'testuser', NOW()),
(11, 'Webcam 4K com Autofoco', 'Webcam com resolução 4K, ideal para streaming e reuniões.', 750.00, 'WEB-4K-001', NOW(), 'testuser', NOW()),
(12, 'Ultrabook Executivo 14"', 'Notebook fino e leve, com foco em portabilidade e bateria.', 6800.00, 'NTB-EXE-001', NOW(), 'testuser', NOW()),
(13, 'HD Externo Portátil 5TB', 'Disco rígido externo com 5TB de capacidade e conexão USB-C.', 600.00, 'HDD-EXT-001', NOW(), 'testuser', NOW()),
(14, 'Roteador Wi-Fi 6E Mesh', 'Sistema de roteadores Mesh para cobertura total com a tecnologia Wi-Fi 6E.', 1300.00, 'ROT-WIFI6-M', NOW(), 'testuser', NOW()),
(15, 'Monitor 4K Profissional 27"', 'Monitor com painel IPS, 99% de cobertura Adobe RGB.', 2900.00, 'MON-4K-001', NOW(), 'testuser', NOW()),
(16, 'Mousepad Gamer Control XXL', 'Mousepad extra grande (900x400mm) com superfície para controle.', 120.00, 'MPD-XXL-001', NOW(), 'testuser', NOW()),
(17, 'Fone Bluetooth com ANC', 'Fone de ouvido com cancelamento de ruído ativo (ANC) e 30h de bateria.', 899.90, 'FNE-ANC-001', NOW(), 'testuser', NOW()),
(18, 'Placa-mãe Z790', 'Placa-mãe compatível com processadores Intel de 13ª e 14ª geração.', 2100.00, 'MBO-Z790-001', NOW(), 'testuser', NOW()),
(19, 'Soundbar 2.1 com Subwoofer', 'Sistema de som para TV com 300W de potência e subwoofer sem fio.', 950.00, 'SND-21-001', NOW(), 'testuser', NOW()),
(20, 'Gabinete Mid-Tower Vidro', 'Gabinete com painel lateral de vidro temperado e bom fluxo de ar.', 450.00, 'GAB-MT-001', NOW(), 'testuser', NOW());

-- Ajusta a sequência do ID para evitar conflitos com futuras inserções via JPA.
-- A função setval irá definir o próximo valor da sequência para o maior ID existente + 1.
-- Isso é CRUCIAL ao inserir IDs manualmente em uma tabela com sequência.
SELECT setval(pg_get_serial_sequence('products', 'id'), (SELECT MAX(id) FROM products));