-- Insert users
-- V2__insert_seed_data.sql

-- ✅ Do not re-insert admin, just add new users  smith123   johnson123
INSERT INTO users (first_name, last_name, password, user_name, email, role)
VALUES
    ('Alice', 'Smith', '$2a$12$xRa0QQJvNymC3FXBN.pP/ODrCPke7AWX9Vg7wd8oquFiIGp6G3L9G', 'alicesmith', 'alice@example.com', 'USER'),
    ('Bob', 'Johnson', '$2a$12$SfnN76h93DteTtpo/yVZHeX.5Q92x2IAJD.b3ZnBreCarZmdi22/O', 'bobjohnson', 'bob@example.com', 'USER'),
    ('Ajay','Roy','$2a$12$AGqFpMtF112nb0gt/cKet.ROGBWBLOFI/WwGX5.Y6a4dvToHdZ/8G','AjayRoy','Ajay7439@gmail.com','ADMIN'),
    ('Admin','sir','$2a$12$3D74FmTA9tlGXECJta.0jOF//nLcnJV9kVtGr2sRgHjzeE8fXx5W.','admin','admin@example.com','ADMIN');


-- Insert products
INSERT INTO products (name, description, price, quantity, category, available, release_date, brand, image_name, image_type, user_id)
VALUES
    ('Laptop', '14-inch ultrabook with 16GB RAM', 74999.99, 10, 'Electronics', true, '2024-05-01', 'TechNova', 'laptop.jpeg', 'image/jpeg', 1),
    ('Smartphone', '5G phone with OLED display', 34999.50, 25, 'Electronics', true, '2024-06-15', 'Mobix', 'image.jpeg', 'image/jpeg', 1),
    ('Desk Chair', 'Ergonomic mesh chair', 8999.00, 8, 'Furniture', true, '2024-04-10', 'ErgoFit', 'deskchair.jpeg', 'image/jpeg', 2);
