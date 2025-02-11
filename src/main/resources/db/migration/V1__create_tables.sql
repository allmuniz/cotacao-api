CREATE TABLE coin_entity (
    id SERIAL PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE wallet_entity (
    id SERIAL PRIMARY KEY,
    principal_coin_id BIGINT,
    CONSTRAINT fk_wallet_coin FOREIGN KEY (principal_coin_id) REFERENCES coin_entity(id) ON DELETE SET NULL
);

CREATE TABLE user_entity (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    principal_balance DOUBLE PRECISION,
    wallet_id BIGINT UNIQUE,
    role VARCHAR(255) NOT NULL ,
    CONSTRAINT fk_user_wallet FOREIGN KEY (wallet_id) REFERENCES wallet_entity(id) ON DELETE CASCADE
);

CREATE TABLE wallet_coin_entity (
    id SERIAL PRIMARY KEY,
    wallet_id BIGINT NOT NULL,
    coin_id BIGINT NOT NULL,
    balance DOUBLE PRECISION,
    notification BOOLEAN,
    value_notification DOUBLE PRECISION,
    CONSTRAINT fk_wallet_coin_wallet FOREIGN KEY (wallet_id) REFERENCES wallet_entity(id) ON DELETE CASCADE,
    CONSTRAINT fk_wallet_coin_coin FOREIGN KEY (coin_id) REFERENCES coin_entity(id) ON DELETE CASCADE
);
