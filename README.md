# Stake Accumulator Service

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Run Tests](#run-tests)
* [API Usage](#api-usage)

## General info
This application is to flag to a Business Operator when a customer, playing a game, 
stake more than £X in a given Ys time window.
	
## Technologies
Project is created with:
* Java 14
* Spring Boot
* Embedded Message Broker ActiveMQ
* H2 embedded MySQL DB
* Liquibase
* Cucumber
* Maven
* Swagger
	
## Setup
To run this project use Maven:

```
./mvnw clean install
./mvnw spring-boot:run
```

Once it's built the service can be also ran like this:

```
java -jar target/stake-accumulator-service-1.0.0.jar
```

The application is available at `localhost:8080`

The threshold values are:
- **Amount**: £100
- **Time Window**: 60 min

These values are set in `application.properties` and can be easily modified.

## Run Tests

```
./mvnw clean test
```

## Swagger

Swagger is available at `http://localhost:8080/swagger-ui.html`

## API Usage

### Create Player Stake

- Request:
```
POST http://localhost:8080/player-stake
Content-Type: application/json

{
  "accountId": "789",
  "stake": 110
}
```

- Response

```
HTTP/1.1 201
```

### Retrieve Player Stake Alerts

- Request

```
GET http://localhost:8080/alert/{{accountId}}
Accept: application/json
```

- Response

```
HTTP/1.1 200
[
   {
      "id":"c1ff690d-ade4-4ade-8559-2bb83d3eec75",
      "accountId":123,
      "cumulatedAmount":160,
      "creationDateTime":"2020-08-22T14:36:12.360333"
   },
   {
      "id":"6b3e2f45-db6a-4512-a449-6f9caf1eeb82",
      "accountId":123,
      "cumulatedAmount":120,
      "creationDateTime":"2020-08-22T14:36:01.842665"
   }
]
```
