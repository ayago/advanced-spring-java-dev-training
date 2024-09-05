-- Insert initial data into Order table
INSERT INTO om_order (status) VALUES ('Pending'), ('Shipped');

-- Insert initial data into OrderItem table
INSERT INTO om_order_item (order_id, product_id, count) VALUES
    (1, 'P001', 10),
    (1, 'P002', 5),
    (2, 'P003', 2);
