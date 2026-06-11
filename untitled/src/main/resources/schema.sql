-- Создайте БД вручную: CREATE DATABASE "f1Shop";
-- Затем выполните этот скрипт, подключившись к f1Shop.

CREATE TABLE IF NOT EXISTS users (
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(20)  NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    role          VARCHAR(10)  NOT NULL DEFAULT 'USER',
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS items (
    id               BIGSERIAL PRIMARY KEY,
    title            VARCHAR(200)  NOT NULL,
    price            BIGINT        NOT NULL CHECK (price > 0),
    description      VARCHAR(1000),
    manufacture_team VARCHAR(100)  NOT NULL,
    owner_id         BIGINT        NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

-- Админ: логин admin, пароль Admin123!
INSERT INTO users (username, password_hash, email, role)
VALUES ('admin', '$2a$10$92LBkfsMJeRJX1/iobvleOgAIYTLC5USSqpwx.Zo1vOC12zkjM1by', 'admin@f1shop.local', 'ADMIN')
ON CONFLICT (username) DO NOTHING;
