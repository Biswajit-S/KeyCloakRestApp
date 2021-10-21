package com.example.KeyCloakRestApp;

import static org.junit.Assert.assertEquals;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
class KeyCloakRestAppApplicationTests {
	
	@Value( "${keycloak.rest.app.host}")
    private String applicationHost;
	
	@Value( "${keycloak.rest.app.movie.finder.uri}")
    private String movieFinderUri;
    
    @Value( "${keycloak.rest.app.movie.rating.uri}")
    private String movieRatingUri;

    @Value( "${keycloak.rest.app.granttype}" )
    private String grantType;
    
    @Value( "${keycloak.rest.app.clientId}" )
    private String clientId;
    
    @Value( "${keycloak.rest.app.clientSecret}" )
    private String clientSecret;
    
    @Value( "${keycloak.rest.app.username}")
    private String username;
    
    @Value( "${keycloak.rest.app.password}")
    private String password;
    
    @Value( "${keycloak.rest.app.tokenurl}")
    private String tokenurl;
    
    @Value( "${keycloak.rest.app.imdbID}")
    private String imdbID;
    
    private static String tokenValue = null;

    
    /*
    @Before
    public void obtainAccessToken() {
    	
        final Map<String, String> params = new HashMap<>();
        params.put("grant_type", grantType);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("username", username);
        params.put("password", password);
        
		
		  Response response = RestAssured.given()
				  						 .contentType("application/x-www-form-urlencoded") 
				  						 .accept("application/json")
				  						 .formParams(params) 
				  						 .post(tokenurl);

        
//        final Response response = RestAssured.given().auth().preemptive().basic(clientId, clientSecret)
//        		.and().with().params(params).when().post(tokenurl);
        
        tokenValue = response.jsonPath().getString("access_token");
        
        
    }
    
    */
    
    @Test
    public void aGetToken() {
    	final Map<String, String> params = new HashMap<>();
        params.put("grant_type", grantType);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("username", username);
        params.put("password", password);
        
		
		  Response response = RestAssured.given()
				  						 .contentType("application/x-www-form-urlencoded") 
				  						 .accept("application/json")
				  						 .formParams(params) 
				  						 .post(tokenurl);
        
		tokenValue = response.jsonPath().getString("access_token");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }
    
    @Test
    public void testMovieFinderAuthorized() {
    	String someMovie = "Toy STory";
    	
    	Response response = RestAssured.given()
    								   .header("Authorization", "Bearer " + tokenValue)
    								   .get(applicationHost + movieFinderUri + someMovie);
    	assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }
    
    @Test
    public void testMovieFinderUnAuthorized() {
    	
    	String someMovie = "Toy STory";
        
    	Response response = RestAssured.given().get(applicationHost + movieFinderUri + someMovie);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
    }
    
    @Test
    public void testMovieRatingAuthorized() throws JSONException {

    	JSONObject jsonObj = new JSONObject()
                .put("imdbID",imdbID)
                .put("rating","2");
        
    	Response response = RestAssured.given()
    			.header("Authorization", "Bearer " + tokenValue)
    			.contentType("application/json")
    			.body(jsonObj.toString())
    			.post(applicationHost + movieRatingUri);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("2000", response.jsonPath().getString("statusCode"));
        assertEquals(imdbID, response.jsonPath().getString("imdbID"));
    }
    
    @Test
    public void testMovieRatingInvalidRatingValue() throws JSONException {

    	JSONObject jsonObj = new JSONObject()
                .put("imdbID",imdbID)
                .put("rating","20");
        
    	Response response = RestAssured.given()
    			.header("Authorization", "Bearer " + tokenValue)
    			.contentType("application/json")
    			.body(jsonObj.toString())
    			.post(applicationHost + movieRatingUri);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("5000", response.jsonPath().getString("statusCode"));
        assertEquals("Rating should be between 1 to 5", response.jsonPath().getString("message"));
    }
    
    
    @Test
    public void testMovieRatingUnAuthorized() throws JSONException {
    	
    	JSONObject jsonObj = new JSONObject()
                .put("imdbID",imdbID)
                .put("rating","2");
        
    	Response response = RestAssured.given()
    			.contentType("application/json")
    			.body(jsonObj.toString())
    			.post(applicationHost + movieRatingUri);
    	assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
    }

}
