-- Create Order table
CREATE TABLE om_order (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50) NOT NULL
);

-- Create OrderItem table
CREATE TABLE om_order_item (
    id SERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id VARCHAR(50) NOT NULL,
    count INTEGER NOT NULL,
    FOREIGN KEY (order_id) REFERENCES om_order(id) ON DELETE CASCADE
);
