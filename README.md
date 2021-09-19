# GlobalWeather Service API

> Sample REST API build with Spring WebFlux

## Requirements

- Java 11
- Spring Boot 2.5.4
- WebFlux 2.5.4

## Installation

```bash
- git clone or download .zip file from this repository
```
- Build the project with Maven 
```bash
mvn package
```
- Run NodeJS server to host the wsdl file locally at http://localhost:8080/GlobalWeather?wsdl
/ note: this is external to the project
```bash
node server.js
```
- Run the project
```bash
java -jar globalweather-0.0.1-SNAPSHOT.jar
```


## Usage


Now open your favorite web-browser (Chrome) and enter: 
```bash
http://localhost:9090/swagger-ui.html
```
Examples:

GetWeather: http://localhost:9090/getWeather/Australia/Melbourne

GetCitiesByCountry: http://localhost:9090/getCities/Australia

/api-docs - http://localhost:9090/api-docs

![swagger-ui](./images/api.png)

## Developed by
Albena Roshelova
