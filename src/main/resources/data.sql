-- Users
INSERT INTO app_user (id, username, role) VALUES (1, 'alice', 'USER');
INSERT INTO app_user (id, username, role) VALUES (2, 'bob', 'USER');
INSERT INTO app_user (id, username, role) VALUES (3, 'carol', 'USER');
INSERT INTO app_user (id, username, role) VALUES (4, 'dave', 'USER');

-- Orders (alice: 4 orders, bob: 2 orders, carol: 0, dave: 5 orders)
INSERT INTO book_order (id, amount, user_id) VALUES (1, 10.0, 1);
INSERT INTO book_order (id, amount, user_id) VALUES (2, 20.0, 1);
INSERT INTO book_order (id, amount, user_id) VALUES (3, 30.0, 1);
INSERT INTO book_order (id, amount, user_id) VALUES (4, 40.0, 1);
INSERT INTO book_order (id, amount, user_id) VALUES (5, 15.0, 2);
INSERT INTO book_order (id, amount, user_id) VALUES (6, 25.0, 2);
INSERT INTO book_order (id, amount, user_id) VALUES (7, 5.0, 4);
INSERT INTO book_order (id, amount, user_id) VALUES (8, 10.0, 4);
INSERT INTO book_order (id, amount, user_id) VALUES (9, 15.0, 4);
INSERT INTO book_order (id, amount, user_id) VALUES (10, 20.0, 4);
INSERT INTO book_order (id, amount, user_id) VALUES (11, 25.0, 4); 