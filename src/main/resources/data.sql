-- Inserting users with encrypted passwords
INSERT INTO users (username, password, enabled, email, created_at)
VALUES ('user1', 'ENCRYPTED_PASSWORD_HERE', TRUE, 'user1@example.com', CURRENT_TIMESTAMP);

INSERT INTO users (username, password, enabled, email, created_at)
VALUES ('admin', 'ENCRYPTED_PASSWORD_HERE', TRUE, 'admin@example.com', CURRENT_TIMESTAMP);

-- Inserting authorities for the users
INSERT INTO authorities (user_id, authority) VALUES ((SELECT id FROM users WHERE username = 'user1'), 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES ((SELECT id FROM users WHERE username = 'admin'), 'ROLE_ADMIN');

-- Inserting categories
INSERT INTO categories (name, description) VALUES ('Electronics', 'Electronic devices and accessories.');
INSERT INTO categories (name, description) VALUES ('Books', 'All kinds of books.');

-- Inserting products
INSERT INTO products (name, description, price, quantity, category_id)
VALUES (
        'Smartphone',
        'Latest model smartphone',
        299.99,
        100,
        (SELECT category_id FROM categories WHERE name = 'Electronics')
    );

INSERT INTO products (name, description, price, quantity, category_id)
VALUES (
        'Laptop',
        'High-performance laptop',
        999.99,
        50,
        (SELECT category_id FROM categories WHERE name = 'Electronics')
    );

INSERT INTO products (name, description, price, quantity, category_id)
VALUES (
        'Fantasy Novel',
        'Bestselling fantasy novel',
        9.99,
        150,
        (SELECT category_id FROM categories WHERE name = 'Books')
    );

-- Inserting an order
INSERT INTO orders (user_id, order_date, status)
VALUES ((SELECT id FROM users WHERE username = 'user1'), CURRENT_TIMESTAMP, 'PROCESSING');

-- Inserting order details
INSERT INTO order_details (order_id, product_id, quantity, price)
VALUES (
        (SELECT order_id FROM orders WHERE user_id = (SELECT id FROM users WHERE username = 'user1')),
        (SELECT product_id FROM products WHERE name = 'Smartphone'),
        1,
        (SELECT price FROM products WHERE name = 'Smartphone')
    );

-- Inserting a cart
INSERT INTO cart (user_id, created_at) VALUES ((SELECT id FROM users WHERE username = 'user1'), CURRENT_TIMESTAMP);

-- Inserting cart items
INSERT INTO cart_items (cart_id, product_id, quantity)
VALUES (
        (SELECT cart_id FROM cart WHERE user_id = (SELECT id FROM users WHERE username = 'user1')),
        (SELECT product_id FROM products WHERE name = 'Fantasy Novel'),
        2
    );
