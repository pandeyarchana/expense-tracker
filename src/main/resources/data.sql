INSERT INTO users (name, email) VALUES ('Alice', 'alice@example.com');
INSERT INTO users (name, email) VALUES ('Bob', 'bob@example.com');
INSERT INTO users (name, email) VALUES ('Charlie', 'charlie@example.com');

INSERT INTO groups (name) VALUES ('Trip to Goa');
INSERT INTO groups (name) VALUES ('Trip to Bangalore');

INSERT INTO group_users (group_id, user_id) VALUES (1, 1);
INSERT INTO group_users (group_id, user_id) VALUES (1, 2);
INSERT INTO group_users (group_id, user_id) VALUES (1, 3);

--INSERT INTO expense ( description, amount, paid_by_id, group_id)
--VALUES ( 'Hotel Booking', 3000.0, 1, 1);
--
--INSERT INTO split ( amount, user_id, expense_id)
--VALUES ( 1000.0, 1, 1),
--       ( 1000.0, 2, 1),
--       ( 1000.0, 3, 1);