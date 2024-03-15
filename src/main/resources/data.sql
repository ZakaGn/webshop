INSERT INTO users (first_name, last_name)
VALUES ('John', 'Doe'),
       ('Jane', 'Smith');

INSERT INTO credentials (email, password, role, user_id)
VALUES ('john.doe@example.com', 'password_hash', 'USER',
        (SELECT id FROM users WHERE first_name = 'John' AND last_name = 'Doe')),
       ('jane.smith@example.com', 'password_hash', 'ADMIN',
        (SELECT id FROM users WHERE first_name = 'Jane' AND last_name = 'Smith'));

INSERT INTO categories (name, description)
VALUES ('Electronics', 'Electronic gadgets and devices.'),
       ('Books', 'A variety of books from various genres.');

INSERT INTO products (name, description, price, quantity, category_id)
VALUES ('Smartphone', 'Latest model with high-resolution camera.', 599.99, 30,
        (SELECT category_id FROM categories WHERE name = 'Electronics')),
       ('Laptop', 'Lightweight and powerful performance.', 1200.00, 20,
        (SELECT category_id FROM categories WHERE name = 'Electronics')),
       ('Fantasy Novel', 'An epic tale of adventure.', 15.99, 100,
        (SELECT category_id FROM categories WHERE name = 'Books'));

INSERT INTO orders (user_id, status)
VALUES ((SELECT id FROM users WHERE first_name = 'John'), 'PENDING');

INSERT INTO order_details (order_id, product_id, quantity, price)
VALUES ((SELECT order_id FROM orders WHERE user_id = (SELECT id FROM users WHERE first_name = 'John')),
        (SELECT product_id FROM products WHERE name = 'Smartphone'), 1, 599.99);

INSERT INTO cart (user_id)
VALUES ((SELECT id FROM users WHERE first_name = 'Jane'));

INSERT INTO cart_items (cart_id, product_id, quantity)
VALUES ((SELECT cart_id FROM cart WHERE user_id = (SELECT id FROM users WHERE first_name = 'Jane')),
        (SELECT product_id FROM products WHERE name = 'Fantasy Novel'), 2);
