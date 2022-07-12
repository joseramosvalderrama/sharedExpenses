# Shared expenses app
## Launch database
```
$ docker run --name mysql -d -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=expenses -p 3306:3306 mysql:8
```


