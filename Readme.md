# Shared expenses app

## Local development
```
$ docker run --name mysql -d -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=expenses -p 3306:3306 mysql:8
$ cd back
$ mvn mn:run
$ cd ../front
$ npm start
```

## Build and launch dockerized app
```
$ docker-compose build
$ docker-compose up 
```

