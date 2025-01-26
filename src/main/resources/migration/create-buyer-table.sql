CREATE TABLE if NOT EXISTS buyer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    card_number BIGINT NOT NULL,
    sale_value BIGINT NOT NULL,

    FOREIGN KEY (order_id) REFERENCES order(id)
);