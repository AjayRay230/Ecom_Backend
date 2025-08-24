-- V4__fix_image_data.sql
-- Permanent fix for image_data column and seed rows

-- 1. Ensure image_data is correctly typed as BYTEA
ALTER TABLE products
ALTER COLUMN image_data TYPE BYTEA
USING NULL;

-- 2. Clean up any corrupted non-bytea data
UPDATE products
SET image_data = NULL
WHERE image_data IS NOT NULL
  AND pg_typeof(image_data)::text <> 'bytea';

-- 3. Optional: Wipe existing bad seed data (keep if you already inserted real user products)
-- Comment out if you want to keep current rows
DELETE FROM products;

-- 4. Re-insert clean sample products with proper NULL for image_data
INSERT INTO products (
    name, description, price, quantity, category, available, release_date,
    brand, image_name, image_type, image_data, user_id
) VALUES
      ('Laptop', '14-inch ultrabook with 16GB RAM', 74999.99, 10, 'Electronics',
       true, '2024-05-01', 'TechNova', 'laptop.jpeg', 'image/jpeg', NULL, 1),

      ('Smartphone', '5G phone with OLED display', 34999.50, 25, 'Electronics',
       true, '2024-06-15', 'Mobix', 'image.jpeg', 'image/jpeg', NULL, 1),

      ('Desk Chair', 'Ergonomic mesh chair', 8999.00, 8, 'Furniture',
       true, '2024-04-10', 'ErgoFit', 'deskchair.jpeg', 'image/jpeg', NULL, 2);
