CREATE TABLE transaction_entity (
                                    id SERIAL PRIMARY KEY,
                                    type VARCHAR(50) NOT NULL,
                                    wallet_id BIGINT NOT NULL,
                                    receiver_coin_id BIGINT,
                                    sender_coin_id BIGINT,
                                    receiver_value DOUBLE PRECISION,
                                    sender_value DOUBLE PRECISION,
                                    date TIMESTAMP NOT NULL,
                                    CONSTRAINT fk_transaction_wallet FOREIGN KEY (wallet_id) REFERENCES wallet_entity(id),
                                    CONSTRAINT fk_transaction_receiver_coin FOREIGN KEY (receiver_coin_id) REFERENCES coin_entity(id),
                                    CONSTRAINT fk_transaction_sender_coin FOREIGN KEY (sender_coin_id) REFERENCES coin_entity(id)
);