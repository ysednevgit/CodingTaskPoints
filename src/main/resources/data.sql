INSERT INTO CUSTOMER (id, first_name, last_name) VALUES (1, 'Yury', 'A');
INSERT INTO CUSTOMER (id, first_name, last_name) VALUES (2, 'Ivan', 'Korob');
INSERT INTO CUSTOMER (id, first_name, last_name) VALUES (3, 'Michael', 'Smith');

INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (1, 20, 1, '2022-08-09');
INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (2, 200, 1, '2022-09-09');
INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (3, 150, 1, '2022-10-09');
INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (4, 300, 1, '2022-11-09');
INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (5, 250, 1, '2022-12-09');
INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (6, 300, 1, '2023-01-09');
INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (7, 100, 1, '2023-01-09');
INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (8, 200, 1, '2023-02-09');

INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (9, 80, 2, '2023-01-09');
INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (10, 150, 2, '2023-02-09');

INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (11, 55, 3, '2023-01-01');
INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (12, 65, 3, '2023-01-31');
INSERT INTO TRANSACTION (id, amount, customer_id, date) VALUES (13, 250, 3, '2023-02-01');