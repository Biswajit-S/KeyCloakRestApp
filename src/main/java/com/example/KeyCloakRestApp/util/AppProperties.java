/**
 * 
 */
package com.example.KeyCloakRestApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author biswajit
 * 
 * Utility class to read a property value from application.properties
 * Intended to manage all the user defined properties at one place.
 * 
 */

@Component
public class AppProperties {
	
	private static String awardsFilePath;
	private static String omdbHost;
	private static String omdbApiKey;
	private static String jwkUri;
	
	@Autowired
	public AppProperties(@Value("${cust.awards.file.path}") String awardsFilePath,
						 @Value("${keycloak.rest.app.omdb.host}") String omdbHost,
						 @Value("${keycloak.rest.app.omdb.api.key}") String omdbApiKey,
						 @Value("${keycloak.rest.app.jwkurl}") String jwkUri) {
		
		AppProperties.awardsFilePath = awardsFilePath;
		AppProperties.omdbHost = omdbHost;
		AppProperties.omdbApiKey = omdbApiKey;
		AppProperties.jwkUri = jwkUri;
	}

	public static String getAwardsFilePath() {
		return awardsFilePath;
	}

	public static String getOmdbHost() {
		return omdbHost;
	}

	public static String getOmdbApiKey() {
		return omdbApiKey;
	}

	public static String getJwkUri() {
		return jwkUri;
	}
	
}
