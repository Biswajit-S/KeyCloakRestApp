package com.example.KeyCloakRestApp.util;

/**
 * @author biswajit
 * 
 * Holds the static fields used in the application.
 * Intended to manage all the static fields at one place. 
 * Improves reusability and modifications.
 * 
 */
public class Constants {
	
	public static final String URI_PATH__PING = "/ping";
	public static final String PING_RESPONSE = "Hello, from the Server!";
	
	public static final String BASE_URI__MOVIES = "/movies";
	
	public static final String URI_PATH__FINDER = "/finder";
	
	public static final String URI_PATH__TOP10 = "/top10";
	
	
	
	public static final String YES = "YES";
	public static final String NO = "NO";
	
	public static final int STATUS_OK = 2000;
	public static final int STATUS_ERR_GENERIC = 5000;


}
