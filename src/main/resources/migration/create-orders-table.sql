CREATE TABLE if NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    price INTEGER NOT NULL,
    buyer_id BIGINT NOT NULL,

    FOREIGN KEY (buyer_id) REFERENCES buyers(id)
    );