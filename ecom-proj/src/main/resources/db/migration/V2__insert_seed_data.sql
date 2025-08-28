-- Insert users
-- V2__insert_seed_data.sql

-- ✅ Do not re-insert admin, just add new users  smith123   johnson123
INSERT INTO users (first_name, last_name, password, user_name, email, role)
VALUES
    ('Alice', 'Smith', '$2a$12$xRa0QQJvNymC3FXBN.pP/ODrCPke7AWX9Vg7wd8oquFiIGp6G3L9G', 'alicesmith', 'alice@example.com', 'USER'),
    ('Bob', 'Johnson', '$2a$12$SfnN76h93DteTtpo/yVZHeX.5Q92x2IAJD.b3ZnBreCarZmdi22/O', 'bobjohnson', 'bob@example.com', 'USER'),
    ('Ajay','Roy','$2a$12$s0xRgJwhrQ2gObE8w/4yleZHtJGp/Z07kIg2XsGGDLpWC1Ub04OsS','AjayRoy','Ajay7439@gmail.com','ADMIN'),
    ('Admin','sir','$2a$12$3D74FmTA9tlGXECJta.0jOF//nLcnJV9kVtGr2sRgHjzeE8fXx5W.','admin','admin@example.com','ADMIN');


-- Insert products
INSERT INTO products (name, description, price, quantity, category, available, release_date, brand, image_url, user_id)
VALUES
    ('Laptop', '14-inch ultrabook with 16GB RAM', 74999.99, 10, 'Electronics', true, '2024-05-01', 'TechNova', 'https://res.cloudinary.com/dbbynmmp4/image/upload/v1756332389/MacBook_pro_M2_kkl1jh.webp', 1),
    ('Smartphone', '5G phone with OLED display', 34999.50, 25, 'Electronics', true, '2024-06-15', 'Mobix', 'https://res.cloudinary.com/dbbynmmp4/image/upload/v1756332426/OIP_phone_image_k0f7qo.jpg', 1),
    ('Desk Chair', 'Ergonomic mesh chair', 8999.00, 8, 'Furniture', true, '2024-04-10', 'ErgoFit', 'https://res.cloudinary.com/dbbynmmp4/image/upload/v1756332448/deskchair_hunv9s.jpg', 2);
