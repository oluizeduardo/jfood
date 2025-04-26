CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    description VARCHAR(300) NOT NULL,
    price NUMERIC(9, 2) NOT NULL
);