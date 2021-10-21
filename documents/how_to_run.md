## Update OMDB API Key in application.properties

```
keycloak.rest.app.omdb.api.key = <OMDB API KEY>
```

## Build the jar file

```
mvn clean install 

[from the pom.xml location]


Skip the unit tests with the below command, in case the unit tests fail. 
As the Unit Test cases expect the KeyCloak to be running to request tokens, 
the unit test cases might fail during the first time run.

mvn clean install -Dmaven.test.skip=true 

```


## Docker Compose Run 

Run Docker Compose to run the stack along with keycloak.

```
docker-compose up

[from the location where docker-compose.yml is present]

```

## Create DB Table

* When the application starts, it creates a database named "moviesdb".
* If it is not created, create the DB.

```

mysql -u root -p Passwd11  # Login

CREATE DATABASE moviesdb;
 
```

* Then create the table "rating" in moviesdb.

```
USE moviesdb;

CREATE TABLE rating (
   imdbID VARCHAR(255) PRIMARY KEY,
   movie_name VARCHAR(255),
   rating DOUBLE,
   voting_count INT
);


```



## Access the Application and APIs

* If the provided files were used, then Key Cloak would have started on port 8080 and the application must be running on port 9090.
* The complete request and response structure is provided in the API-Spec.md and the postman collections.

```
* Token Endpoint: http://localhost:8080/auth/realms/Backbase/protocol/openid-connect/token
* Movie Search Endpoint:http://localhost:9090/services/movies/finder/<movie title>
* Movie Rating API Endpoint: http://localhost:9090/services/movies/rating
```

## Run Stand-alone java application on Docker [INFROMATIONAL]

(Docker Compose should be run to start keycloak as well. This section is for reference only)

```
docker run -p 9090:9090 sahoobiswajit/keycloakrestapp:latest

```
 
## Run Stand-alone java application command line

If the docker compose fail to run the application, it can be started locally from command line.

```
mvnw spring-boot:run 

```
  
