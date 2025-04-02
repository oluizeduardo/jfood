CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role INTEGER NOT NULL CHECK (role IN (1, 2))
);

CREATE TABLE menu_items (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0)
);
