DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM users;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO RESTAURANTS (name, address) VALUES
  ('kfc', 'addressKfc'),
  ('mcDonalds', 'addressMcDonalds'),
  ('ketchup', 'addressKetchup'),
  ('bushe', 'addressBushe');
--   kfc  100000
-- mcDonalds 100001
-- ketchup 100002
-- bushe 100003


INSERT INTO dishes (name, price, restaurant_id, date) VALUES
  ('chicken',12000 ,100000, '2018-07-25'),
  ('fries', 12500, 100000, '2018-07-25'),
  ('cola', 8000, 100000, '2018-07-25'),
  ('cheesburger',15020, 100001, '2018-07-26'),
  ('hamburger', 12500, 100001, '2018-07-26'),
  ('fishburger', 9000, 100001, '2018-07-26'),
  ('ketchup_burger', 25000, 100002, '2018-07-27'),
  ('salad', 20000, 100002, '2018-07-27'),
  ('water', 7000, 100002, '2018-07-27'),
  ('cake', 18080, 100003, '2018-07-27'),
  ('bread', 9080, 100003, '2018-07-27'),
  ('coffee', 10080, 100003, '2018-07-27'),
  ('chicken_special',12000 ,100000, CURRENT_DATE),
  ('cheesburger_special',15020, 100001, '2018-07-26'),
  ('ketchup_burger_special', 25000, 100002, CURRENT_DATE),
  ('cake_special', 18080, 100003, CURRENT_DATE);


INSERT INTO users (name, email, password, role) VALUES
  ('User1', 'user1@yandex.ru', '{noop}password', 'ROLE_USER'),
  ('User2', 'user2@mail.ru', '{noop}password', 'ROLE_USER'),
  ('Admin', 'admin@gmail.com', '{noop}admin', 'ROLE_ADMIN');
-- User1 100020
-- User2 100021
-- Admin 100022

INSERT INTO votes (date, USER_ID, RESTAURANT_ID) VALUES
  ('2018-07-25', 100020, 100000),
  ('2018-07-25', 100021, 100003),
  (CURRENT_DATE, 100021, 100002);





