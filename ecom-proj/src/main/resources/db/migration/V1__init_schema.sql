-- USERS table
CREATE TABLE users (
                       user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100),
                       password VARCHAR(255) NOT NULL,
                       user_name VARCHAR(100) NOT NULL UNIQUE,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       role VARCHAR(50) NOT NULL,
                       time_stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- PRODUCTS table
CREATE TABLE products (
                          id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DECIMAL(15,2) NOT NULL,
                          quantity INT NOT NULL,
                          category VARCHAR(100),
                          available BOOLEAN NOT NULL,
                          release_date DATE,
                          brand VARCHAR(100),
                          image_name VARCHAR(255),
                          image_type VARCHAR(100),
                          image_data BYTEA,

    -- Foreign key to USERS
                          user_id INT,
                          CONSTRAINT fk_user FOREIGN KEY (user_id)
                              REFERENCES users(user_id)
                              ON DELETE CASCADE
);
