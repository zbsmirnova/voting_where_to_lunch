DELETE FROM votes;
DELETE FROM user_roles;
DELETE FROM dishes;
DELETE FROM users;
DELETE FROM restaurants;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO restaurants (name) VALUES
  ('kfc'),
  ('mcDonalds'),
  ('ketchup'),
  ('bushe');

INSERT INTO menus (menu_date, restaurant_id) VALUES
  ('2018-07-25', '100001'),
  ('2018-07-25', '100002'),
  ('2018-07-25', '100003'),
  ('2018-07-25', '100004'),
  (CURRENT_DATE, '100001'),
  (CURRENT_DATE, '100002'),
  (CURRENT_DATE, '100003'),
  (CURRENT_DATE, '100004');

INSERT INTO dishes (name, price, menu_id) VALUES
  ('chicken','12000' ,'100005'),
  ('cheesburger','15020', '100006'),
  ('ketchup_burger', '25000', '100007'),
  ('cake', '18080', '100008'),
  ('chicken_special','12000' ,'100009'),
  ('cheesburger_special','15020', '100010'),
  ('ketchup_burger_special', '25000', '100011'),
  ('cake_special', '18080', '100012');


INSERT INTO users (name, email, password) VALUES
  ('User1', 'user1@yandex.ru', 'password'),
  ('User2', 'user2@mail.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO votes (voting_date, user_id, restaurant_id) VALUES
  ('2018-07-25', 100013, 100005, 100001),


INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100013),
  ('ROLE_USER', 100014),
  ('ROLE_ADMIN', 100015);



