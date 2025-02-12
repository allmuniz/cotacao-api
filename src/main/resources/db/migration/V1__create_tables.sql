-- Criação da tabela 'user_entity'
CREATE TABLE user_entity (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             email VARCHAR(255) NOT NULL UNIQUE,
                             password VARCHAR(255) NOT NULL,
                             role VARCHAR(255) NOT NULL
);

-- Criação da tabela 'wallet_entity'
CREATE TABLE wallet_entity (
                               id SERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL, -- Chave estrangeira para UserEntity
                               CONSTRAINT fk_wallet_user FOREIGN KEY (user_id) REFERENCES user_entity(id) ON DELETE CASCADE
);

-- Criação da tabela 'coin_entity'
CREATE TABLE coin_entity (
                             id SERIAL PRIMARY KEY,
                             code VARCHAR(10) NOT NULL,
                             balance DOUBLE PRECISION,
                             notification BOOLEAN,
                             value_notification DOUBLE PRECISION,
                             is_principal BOOLEAN DEFAULT FALSE,
                             wallet_id BIGINT NOT NULL,  -- Chave estrangeira para WalletEntity
                             CONSTRAINT fk_coin_wallet FOREIGN KEY (wallet_id) REFERENCES wallet_entity(id) ON DELETE CASCADE
);