Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.

AdminController
#### get User 100021
`curl -s http://localhost:8080/admin/users/100021 --user admin@gmail.com:admin`

#### get All Users
`curl -s http://localhost:8080/admin/users --user admin@gmail.com:admin`

#### delete User
`curl -s -X DELETE http://localhost:8080/admin/users/100021 --user admin@gmail.com:admin`

#### create User
`curl -s -X POST -d ' {"name": "newUser", "email": "user@bk.ru", "password": "{noop}password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/admin/users --user admin@gmail.com:admin`

#### update User 100021
`curl -s -X PUT -d ' {"name": "updatedUser1", "email": "user1@yandex.ru", "password": "{noop}password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/admin/users/100020 --user admin@gmail.com:admin`

ProfileController
#### get Authorized User
`curl -s http://localhost:8080/profile --user user1@yandex.ru:password`

#### delete Authorized User
`curl -s -X DELETE http://localhost:8080/profile --user user1@yandex.ru:password`

#### update Authorized User
`curl -s -X PUT -d ' {"name": "user1_updated", "email": "user1@yandex.ru", "password": "{noop}password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/profile --user user1@yandex.ru:password`

AdminRestaurantController
#### get RestaurantTo 100000
`curl -s http://localhost:8080/admin/restaurants/100000 --user admin@gmail.com:admin`

#### get All Restaurants
`curl -s http://localhost:8080/admin/restaurants --user admin@gmail.com:admin`

#### get All Restaurants With Today Menu (returns only restaurants with not empty today menu)
`curl -s http://localhost:8080/admin/restaurants/getAllWithTodayMenu --user admin@gmail.com:admin`

#### delete Restaurant
`curl -s -X DELETE http://localhost:8080/admin/restaurants/100001 --user admin@gmail.com:admin`

#### create Restaurant
`curl -s -X POST -d '  { "name": "new", "address": "addressNew"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/admin/restaurants --user admin@gmail.com:admin`

#### update Restaurant 100000
`curl -s -X PUT -d ' {"name": "kfcUpd", "address": "addressKfcUpd"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/admin/restaurants/100000 --user admin@gmail.com:admin`

ProfileRestaurantController
#### get RestaurantTo 100000
`curl -s http://localhost:8080/profile/restaurants/100000 --user user1@yandex.ru:password`

#### get All Restaurants With Today Menu (returns only restaurants with not empty today menu)
`curl -s http://localhost:8080/profile/restaurants --user user1@yandex.ru:password`

AdminDishController
#### get Dish 100004 for Restaurant 100000
`curl -s http://localhost:8080/admin/restaurants/100001/dishes/100004 --user admin@gmail.com:admin`

#### get all Dishes for Restaurant 100000
`curl -s http://localhost:8080/admin/restaurants/100000/dishes --user admin@gmail.com:admin`

#### get all today Dishes for Restaurant 100000
`curl -s http://localhost:8080/admin/restaurants/100000/dishes/today --user admin@gmail.com:admin`

#### delete Dish 100004 for Restaurant 100000
`curl -s -X DELETE http://localhost:8080/admin/restaurants/100000/dishes/100004 --user admin@gmail.com:admin`

#### create Dish for Restaurant 100000
`curl -s -X POST -d '  {"name":"newDish","price":12000,"date":"2018-09-10"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/admin/restaurants/100000/dishes --user admin@gmail.com:admin`

#### update Dish 100004 for Restaurant 100000
`curl -s -X PUT -d ' {"name":"updatedDish","price":1000,"date":"2018-09-11"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/admin/restaurants/100000/dishes/100004 --user admin@gmail.com:admin`

AdminVoteController
#### get Vote 100023
`curl -s http://localhost:8080/admin/votes/100023 --user admin@gmail.com:admin`

#### get all Votes
`curl -s http://localhost:8080/admin/votes --user admin@gmail.com:admin`

#### get all today Votes
`curl -s http://localhost:8080/admin/votes/today --user admin@gmail.com:admin`

#### delete Vote 100023
`curl -s -X DELETE http://localhost:8080/admin/votes/100023 --user admin@gmail.com:admin`

ProfileVoteController
#### get Authorized User`s today Vote
`curl -s http://localhost:8080/profile/votes --user user1@yandex.ru:password`

#### createOrUpdate Authorized User`s Vote for Restaurant 100000
`curl -s -X POST -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/profile/restaurants/100000/votes --user user1@yandex.ru:password`
