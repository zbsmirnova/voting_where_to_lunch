DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM MENUS;
DELETE FROM users;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO RESTAURANTS (NAME) VALUES
  ('kfc'),
  ('mcDonalds'),
  ('ketchup'),
  ('bushe');

INSERT INTO menus (date, restaurant_id) VALUES
  ('2018-07-25', 100000),
  ('2018-07-25', 100001),
  ('2018-07-25', 100002),
  ('2018-07-25', 100003),
  (CURRENT_DATE, 100000),
  ('2018-05-25', 100001),
  (CURRENT_DATE, 100002),
  (CURRENT_DATE, 100003);

INSERT INTO dishes (name, price, menu_id) VALUES
  ('chicken',12000 ,100004),
  ('fries', 12500, 100004),
  ('cola', 8000, 100004),
  ('cheesburger',15020, 100005),
  ('hamburger', 12500, 100005),
  ('fishburger', 9000, 100005),
  ('ketchup_burger', 25000, 100006),
  ('salad', 20000, 100006),
  ('water', 7000, 100006),
  ('cake', 18080, 100007),
  ('bread', 9080, 100007),
  ('coffee', 10080, 100007),
  ('chicken_special',12000 ,100008),
  ('cheesburger_special',15020, 100009),
  ('ketchup_burger_special', 25000, 100010),
  ('cake_special', 18080, 100011);


INSERT INTO users (name, email, password, role) VALUES
  ('User1', 'user1@yandex.ru', '{noop}password', 'ROLE_USER'),
  ('User2', 'user2@mail.ru', '{noop}password', 'ROLE_USER'),
  ('Admin', 'admin@gmail.com', '{noop}admin', 'ROLE_ADMIN');

INSERT INTO votes (voteDate, user_id, restaurant_id) VALUES
  ('2018-07-25', 100028, 100000),
  ('2018-07-25', 100029, 100003),
  (CURRENT_DATE, 100029, 100002);





