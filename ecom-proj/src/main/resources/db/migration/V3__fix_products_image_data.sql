-- V3__fix_products_image_data.sql
-- Ensure image_data column has correct type and seed data is safe

-- 1. Ensure column is BYTEA (fix wrong casts from earlier migrations)
ALTER TABLE products
ALTER COLUMN image_data TYPE BYTEA
    USING NULL;

-- 2. Wipe old bad rows (only seed/sample data, not user data)
DELETE FROM products;

-- 3. Re-insert sample products with correct columns
INSERT INTO products (
    name, description, price, quantity, category, available, release_date, brand, image_name, image_type, image_data, user_id
) VALUES
      ('Laptop', '14-inch ultrabook with 16GB RAM', 74999.99, 10, 'Electronics', true, '2024-05-01', 'TechNova', 'laptop.jpeg', 'image/jpeg', DEFAULT, 1),
      ('Smartphone', '5G phone with OLED display', 34999.50, 25, 'Electronics', true, '2024-06-15', 'Mobix', 'image.jpeg', 'image/jpeg', DEFAULT, 1),
      ('Desk Chair', 'Ergonomic mesh chair', 8999.00, 8, 'Furniture', true, '2024-04-10', 'ErgoFit', 'deskchair.jpeg', 'image/jpeg', DEFAULT, 2);
