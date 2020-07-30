/* Populate tables */
INSERT INTO users(name, lastname, email, created, modified, photo) VALUES ('Chuck', 'Norris', 'chucknorris@gmail.com', now(), now(), '');
INSERT INTO users(name, lastname, email, created, modified, photo) VALUES ('Juanito', 'Perez', 'juanit.perez@gmail.com', now(), now(), '');

INSERT INTO products(name, price, stock, created, photo) VALUES ('Audifonos HP', 18990, 45, now(), 'audifonos.png');
INSERT INTO products(name, price, stock, created, photo) VALUES ('Mouse Gamer Redragon', 14990, 50, now(), 'mouse.jpg');
INSERT INTO products(name, price, stock, created, photo) VALUES ('LG SmartTV 55', 199990, 20, now(), 'smartTV.jpg');
INSERT INTO products(name, price, stock, created, photo) VALUES ('Mousepad Genius', 8990, 100, now(), 'mousepad.jpg');
INSERT INTO products(name, price, stock, created, photo) VALUES ('Teclado Gamer Redragon', 21990, 100, now(), 'teclado.jpg');

INSERT INTO bills(description, created, user_id) VALUES('GAMER Pack 2020', now(), 1);

INSERT INTO bill_details(quantity, product_id, bill_id) VALUES (5, 1, 1);
INSERT INTO bill_details(quantity, product_id, bill_id) VALUES (2, 2, 1);
INSERT INTO bill_details(quantity, product_id, bill_id) VALUES (1, 4, 1);
INSERT INTO bill_details(quantity, product_id, bill_id) VALUES (3, 5, 1);