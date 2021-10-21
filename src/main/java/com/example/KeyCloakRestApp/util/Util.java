package com.example.KeyCloakRestApp.util;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.example.KeyCloakRestApp.bean.MovieBean;

import com.opencsv.CSVReader;

/**
 * @author biswajit
 * 
 * 
 */

public class Util {
	
	/**
	 * Accepts a movie name
	 * Reads the academy_awards.csv file to check and returns all the matches for the movie. 
	 * Uses OpenCSV to read the CSV file. 
	 * 
	 * http://opencsv.sourceforge.net/#reading_into_beans
	 * 
	 * @param String
	 * @return List<MovieBean>
	 *
	 */
	
	public List<MovieBean> getMovieBeanByName(String name) {
		
		List<MovieBean> movieBeans = new ArrayList<>();
		MovieBean movieBean;
		
		try {
			
			URL res = getClass().getClassLoader().getResource(AppProperties.getAwardsFilePath());
			File file = Paths.get(res.toURI()).toFile();			
			CSVReader csvReader = new CSVReader(new FileReader(file));
            
            String[] nextLine;
            csvReader.readNext(); // Skip Headers
            while ((nextLine = csvReader.readNext()) != null) {
            	if(nextLine[2].equals(name)) {
            		movieBean = new MovieBean();
            		movieBean.setYear(nextLine[0]);
            		movieBean.setCategory(nextLine[1]);
            		movieBean.setNominee(nextLine[2]);
            		movieBean.setHasWon(nextLine[4]);
            		
            		movieBeans.add(movieBean);
            	}
            }
            
            csvReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return movieBeans;
	}
	
	/**
	 * Accepts a movie name
	 * Make a call to OMDB API to search the movie title and returns the response as string.
	 * 
	 * @param String
	 * @return String
	 *
	 */
	
	public String serachTop10MoviesByTitle(String movieName) {
		final String url = AppProperties.getOmdbHost() + "?apikey=" + 
									AppProperties.getOmdbApiKey() +"&type=movie&s=" + movieName;
		return restApiRequest(url);
	}
	
	
	/**
	 * Accepts a imdbID
	 * Make a call to OMDB API to search the movie title and returns the response as string.
	 * 
	 * @param String
	 * @return String
	 *
	 */
	
	public String serachMovieByImdbId(String imdbID) {
		final String url = AppProperties.getOmdbHost() + "?apikey=" + 
									AppProperties.getOmdbApiKey() + "&i=" + imdbID;
		return restApiRequest(url);
	}
	
	
	/**
	 * Private method to make HTTP REST calls.
	 * Accepts an URL, make a HTTP call and returns the response as string.
	 * 
	 * @param String
	 * @return String
	 *
	 */
	
	private String restApiRequest(String url) {
	    RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, String.class);
	}
	
	/**
	 * Converts Object class to String.
	 * If the STring from OMDB API response contains "N/A", sets it as null.
	 * 
	 * @param Object
	 * @return String
	 *
	 */
	
	public String formatValue(Object obj) {
		
		String str = obj.toString();
		
		if(str.equals("N/A")) {
			str = null;
		}
		
		return str;
	}
	
	/**
	 * Converts box office collection value from String to long.
	 * 
	 * @param String
	 * @return long
	 *
	 */
	
	public static long formatNumber(String str) {
		
		long num = 0;

		if (str != null) {
			num = Long.parseLong(str.replace("$", "").replace(",", ""));
		}

		return num;
	}

}
