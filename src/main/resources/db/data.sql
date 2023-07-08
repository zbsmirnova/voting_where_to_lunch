DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM users;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO RESTAURANTS (id, name, address) VALUES
  (10000, 'kfc', 'addressKfc'),
  (10001, 'mcDonalds', 'addressMcDonalds'),
  (10002, 'ketchup', 'addressKetchup'),
  (10003, 'bushe', 'addressBushe');

INSERT INTO dishes (id, name, price, restaurant_id, date) VALUES
  (10004, 'chicken',12000 ,10000, '2018-07-25'),
  (10005, 'fries', 12500, 10000, '2018-07-25'),
  (10006, 'cola', 8000, 10000, '2018-07-25'),
  (10007, 'cheesburger',15020, 10001, '2018-07-26'),
  (10008, 'hamburger', 12500, 10001, '2018-07-26'),
  (10009, 'fishburger', 9000, 10001, '2018-07-26'),
  (10010, 'ketchup_burger', 25000, 10002, '2018-07-27'),
  (10011, 'salad', 20000, 10002, '2018-07-27'),
  (10012, 'water', 7000, 10002, '2018-07-27'),
  (10013, 'cake', 18080, 10003, '2018-07-27'),
  (10014, 'bread', 9080, 10003, '2018-07-27'),
  (10015, 'coffee', 10080, 10003, '2018-07-27'),
  (10016, 'chicken_special',12000 ,10000, CURRENT_DATE),
  (10017, 'cheesburger_special',15020, 10001, '2018-07-26'),
  (10018, 'ketchup_burger_special', 25000, 10002, CURRENT_DATE),
  (10019, 'cake_special', 18080, 10003, CURRENT_DATE);

INSERT INTO users (id, email, name, password, role) VALUES
  (10020, 'user1@yandex.ru', 'Name1', '{noop}password', 'USER'),
  (10021, 'user2@mail.ru', 'User2', '{noop}password', 'USER'),
  (10022, 'admin@gmail.com', 'Admin', '{noop}admin', 'ADMIN');

INSERT INTO votes (id, date, USER_ID, RESTAURANT_ID) VALUES
    (10023, '2018-07-25', 10020, 10000),
    (10024, '2018-07-25', 10021, 10003),
    (10025, CURRENT_DATE, 10021, 10002);




