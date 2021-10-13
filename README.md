# What I talk about when I talk about...
# Microservices Oriented Architectures - Event Driven Architectures using EVENTUATE

This training requires some components to be run before executing any service.

## RabbitMQ

```shell
docker pull rabbitmq:3-management
docker run -d --hostname my-rabbit -p 5672:5672 -p 15672:15672 --name some-rabbit rabbitmq:3-management
docker exec some-rabbit rabbitmq-plugins enable rabbitmq_consistent_hash_exchange
```

## ZooKeeper

```shell
docker pull zookeeper
docker run -d -p 2181:2181 --name some-zookeeper --restart always -d zookeeper
```

## MySQL 
```shell
docker pull mysql:5.6
docker run -d -p 3306:3306 --name some-mysql -e MYSQL_ROOT_PASSWORD=password mysql:5.6

mysql -u root -h 127.0.0.1 -P 3306 -p < sql_scripts/bank_registration.sql
mysql -u root -h 127.0.0.1 -P 3306 -p < sql_scripts/oath.sql
```

## EVENTUATE CDC Service
```shell
 docker pull eventuateio/eventuate-tram-cdc-mysql-service
 docker run -d --network host --env-file cdc_service/environment.txt --name cdc-service eventuateio/eventuate-tram-cdc-mysql-service
 docker logs cdc-service
```

## Java Components

Java Development Kit (JDK) 11 is required to build the components

```shell
cd workspace/domain-events-commons
mvn clean install
cd ../bankregistration-api
mvn clean package
cd ../oath-api
mvn clean package
```

If you wish to run on console the components:

```shell
java -jar -Dspring.profiles.active=local target/[artifact-id]-[version].jar
```


## Destroy all components
```shell
docker stop cdc-service
docker rm cdc-service

docker stop some-rabbit
docker rm some-rabbit

docker stop some-zookeeper
docker rm some-zookeeper

docker stop some-mysql
docker rm some-mysql

```
