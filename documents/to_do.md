## General

* In the code, wherever further enhancement is feasible, is explained with a block comment starting with [EXTENSION].

## Application

* The current rating implementation expects the imdbID to be provided in the request. This feature can be extended by using already implemented findByMovieName() method extract the Rating object by Movie Name, in case the same is provided instead of imdbID. 
* The application does not implement any custom exception. The code can be improvised using custom exception class.
* The application ignores the user input errors. The same can be improvised. 
* Same with the HTTP response code, returned for user defined errors. Proper HTTP codes can be implemented. For example, if the provides an incorrect rating value in the input (let's say 20), though the app returns a message stating this rating value is not allowed, but with a HTTP status code 200. A 400 code can be returned in this case.

## Unit Testing
* The unit test cases cove only the security features and a success/unsuccessful response from the server. More test cases can be added to verify the awards, box office etc.
* The unit test cases expects a valid AccessToken.
* Currently the pre-setup (with @Before in obtainAccessToken()) is not executing while running the tests. So the access token could not be generated from the Test Classes automatically, which require further investigation.
* For timebeing a workaround is in place to generate the Access Token.
* A test case aGetToken(), starting with "a", is written to fetch the Access Token by calling the KeyCloak Token endpoint.
* Since JUnit runs the tests alphabetically, it guaranties to be executed first.

## Docker Set-up
* Intermittently the connection from the SpringBoot application container to MySQL container fails within Docker environment, when the stack is created using docker compose. I assume the issue is within the Docker networking, which needs further investigation.
* Meantime, the standalone SpringBoot application can be started outside Docker environment.

```
mvnw spring-boot:run

```
