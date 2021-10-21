package com.example.KeyCloakRestApp.business;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.KeyCloakRestApp.bean.Award;
import com.example.KeyCloakRestApp.bean.MovieBean;
import com.example.KeyCloakRestApp.bean.Rating;
import com.example.KeyCloakRestApp.data.RatingRepository;
import com.example.KeyCloakRestApp.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.example.KeyCloakRestApp.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class handles the business logic for both movie search and rating.
 *
 */

@Service
public class MovieFinderService {
	
	// Initialize the JPA repository to interact with the "rating" table.
	@Autowired
	private RatingRepository ratingRepository;
	
	Util util = new Util();
	
	/**
	 * This service checks in academy_awards.csv, if a movie has won any awards.
	 * 
	 * @param String
	 * @return MovieBean
	 */
	private MovieBean findOscarNomination(MovieBean movie) {
		
		List<Award> awards = new ArrayList<>();
		Award award;
		
		List<MovieBean> movieBeans = util.getMovieBeanByName(movie.getMovieName());

		for (MovieBean movieBean : movieBeans) {
			if(movieBean.getHasWon().equals(Constants.YES)) {
				award = new Award();
				
				award.setYear(movieBean.getYear());
				award.setCategory(movieBean.getCategory());
				
				awards.add(award);
			}
		}
		
		if(awards.size() == 0) {
			// The movie has not won any awards.
			movie.setHasWon(Constants.NO);
			return movie;
		}
		
		movie.setHasWon(Constants.YES);
		movie.setAwards(awards);
		
		return movie;
	}
	
	/**
	 * This service makes a call to OMDB API to get the top 10 results for the title provided by the use.
	 * Calls the findOscarNomination() service for each movie to check, if it has won any awards.
	 * Prepares a list of results with the award details by sorting based on box office collection.
	 * 
	 * @param String
	 * @return List<MovieBean>
	 */
	
	public List<MovieBean> getTop10Movies(String movieName) throws JsonMappingException, JsonProcessingException {
		
		List<MovieBean> movies = new ArrayList<>();
		MovieBean movieBean;
	    
	    JSONObject jsonSerchResult = new JSONObject(util.serachTop10MoviesByTitle(movieName));
	    JSONArray resultArr = jsonSerchResult.getJSONArray("Search");
		
		for(int i=0; i < resultArr.length(); i++) {
			JSONObject tempObj = new JSONObject(resultArr.get(i).toString());
			movieBean = new MovieBean();

			String imdbID = util.formatValue(tempObj.get("imdbID"));
			
			movieBean.setMovieName(util.formatValue(tempObj.get("Title")));
			movieBean.setImdbID(imdbID);
			movieBean.setPoster(util.formatValue(tempObj.get("Poster")));
			
			movieBean = this.findOscarNomination(movieBean);
			
			JSONObject jsonMovieRes = new JSONObject(util.serachMovieByImdbId(imdbID));
			
			String boxOffice = util.formatValue(jsonMovieRes.get("BoxOffice"));
			movieBean.setBoxOffice(boxOffice);
			
			movies.add(movieBean);
			
			Collections.sort(movies);

		}
		
		return movies;
	}
	
	/**
	 * This service is responsible for providing a rating for the movies.
	 * Allows to provide rating for a movie, based on the imdb ID.
	 * 
	 * If the API transaction is successful, it returns a status code as 2000, 
	 * imdb ID of the movie for which rating has been received, average rating based on all the submissions, 
	 * total number of votes received for this movie.
	 * 
	 * If the user provides a invalid rating, an error message is returned.
	 * 
	 * The ratings are stored in a external Databse.
	 * 
	 * @param Rating
	 * @return Rating
	 */
	
	public Rating rateingService(Rating rating) {
		
		double inputRating = rating.getRating();
		
		int updatedVotingCount = 1;
		double newAvgRating = inputRating;
		
		if (inputRating < 1 || inputRating > 5) {
			// throw custom exception (Rating should be between 1 to 5)
			
			Rating ratingStatus = new Rating();
			ratingStatus.setStatus(Constants.STATUS_ERR_GENERIC, "Rating should be between 1 to 5");
			return ratingStatus;
		}
		
		/*
		 * [EXTENSION]
		 * 
		 * The current rating implemented expects the imdbID to be provided in the request.
		 * 
		 * This feature can be extended by using already implemented findByMovieName() method
		 * to extract the Rating object by Movie Name, in case the same is provided instead of imdbID. 
		 * 
		 */
		Rating dbRating = ratingRepository.findById(rating.getImdbID())
									.orElse(null);
		
		if(dbRating != null) {
			DecimalFormat df = new DecimalFormat("#.#");
			double currentVotingCount = dbRating.getVotingCount();
			double calculatedNewAvg = ((dbRating.getRating() * currentVotingCount) + inputRating) / (currentVotingCount + 1);
			newAvgRating = Double.parseDouble(df.format(calculatedNewAvg));
			
			updatedVotingCount += currentVotingCount;
		}
		else {
			dbRating = rating;
		}
		
		dbRating.setRating(newAvgRating);
		dbRating.setVotingCount(updatedVotingCount);
		
		ratingRepository.save(dbRating);
		
		dbRating.setStatusCode(Constants.STATUS_OK);
		
		return dbRating;
	}


}
