# Shared expenses app

## Build app for version 0.1.0
```
$ docker build -t shared_expenses_back:0.1.0 back/
$ docker build -t shared_expenses_front:0.1.0 front/
```

## Launch database
```
$ docker run --name mysql -d -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=expenses -p 3306:3306 mysql:8
```

## Launch backend app
```
$ cd back
$ docker run -p 8080:8080 -d --network=host shared_expenses_back:0.1.0
```
## Launch frontend app
```
$ cd front
$ docker run -p 3000:3000 -d --network=host shared_expenses_front:0.1.0
```

