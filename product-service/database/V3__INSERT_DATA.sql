-- ============================================
-- Migration: V3__INSERT_DATA.sql
-- ============================================

INSERT INTO products (name, description, price, stock, category, created_by) VALUES
('Laptop Dell XPS 15', 'Laptop empresarial de alta gama', 1299.99, 15, 'Electronics', 1),
('Mouse Logitech MX Master 3', 'Mouse inalámbrico ergonómico', 99.99, 50, 'Electronics', 1),
('Teclado Mecánico Keychron K8', 'Teclado mecánico RGB', 89.99, 30, 'Electronics', 2),
('Monitor LG UltraWide 34"', 'Monitor curvo UltraWide', 449.99, 8, 'Electronics', 2),
('Auriculares Sony WH-1000XM5', 'Auriculares con cancelación de ruido', 349.99, 20, 'Electronics', 3);




INSERT INTO restaurants (name, description, address, phone)
VALUES
    ('Sabor Criollo', 'Restaurante especializado en comida criolla peruana', 'Av. Arequipa 1250, Lima', '987654321'),
    ('El Buen Ceviche', 'Cevichería con platos tradicionales de la costa peruana', 'Av. La Marina 2450, San Miguel', '986543210'),
    ('Antojitos del Perú', 'Comida peruana tradicional y casera', 'Jr. de la Unión 850, Lima', '985432109'),
    ('La Casa del Lomo', 'Especialidad en lomo saltado y carnes al wok', 'Av. Benavides 1850, Miraflores', '984321098'),
    ('Sabores de Arequipa', 'Cocina tradicional arequipeña', 'Av. Ejército 720, Arequipa', '983210987'),
    ('El Rincón Norteño', 'Platos típicos de la gastronomía norteña', 'Av. España 1250, Trujillo', '982109876'),
    ('Pachamanka', 'Cocina tradicional de la sierra peruana', 'Av. Huancavelica 540, Huancayo', '981098765'),
    ('Mar y Tierra', 'Restaurante especializado en pescados, mariscos y carnes', 'Av. Costanera 1450, Callao', '980987654'),
    ('Dulce Perú', 'Postres tradicionales y bebidas peruanas', 'Av. Primavera 890, Santiago de Surco', '979876543'),
    ('La Casona Peruana', 'Cocina peruana tradicional con platos regionales', 'Jr. Puno 430, Cusco', '978765432');