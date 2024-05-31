INSERT INTO shop_users (name, login) VALUES ('Іван Іванов', 'ivanov'), ('Петро Петров', 'petrov');

INSERT INTO products (name, description, price, quantity, image_url) VALUES
('Продукт 1', 'Опис продукту 1', 100.0, 10, 'url_to_image1'),
('Продукт 2', 'Опис продукту 2', 200.0, 20, 'url_to_image2');

INSERT INTO carts (user_id, total_price) VALUES (1, 0.0), (2, 0.0);

INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (1, 1, 1), (2, 2, 1);

INSERT INTO orders (user_id, cart_id, order_date) VALUES (1, 1, CURRENT_TIMESTAMP), (2, 2, CURRENT_TIMESTAMP);

INSERT INTO blacklists (user_id) VALUES (1);