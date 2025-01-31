/*CREATE TABLE if NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    price INTEGER NOT NULL,
    buyer_id BIGINT NOT NULL,

    FOREIGN KEY (buyer_id) REFERENCES buyers(id)
    );*/
CREATE TABLE IF NOT EXISTS orders (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "buyer_id BIGINT NOT NULL, " +
                "shop_id BIGINT NOT NULL, " +
                "product_id BIGINT NOT NULL " +
                "CONSTRAINT fk_buyer FOREIGN KEY (buyer_id) REFERENCES buyer(id), " +
                "CONSTRAINT fk_shop FOREIGN KEY (shop_id) REFERENCES shop(id)" +
                "CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id)" +
                ");