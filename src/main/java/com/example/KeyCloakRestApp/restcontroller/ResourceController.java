package com.example.KeyCloakRestApp.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KeyCloakRestApp.bean.MovieBean;
import com.example.KeyCloakRestApp.bean.Rating;
import com.example.KeyCloakRestApp.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.example.KeyCloakRestApp.business.MovieFinderService;

/**
 * @author biswajit
 * 
 * Exposes REST APIs and is the entry point for all the APIs.
 *
 */

@RestController
@RequestMapping(Constants.BASE_URI__MOVIES)
public class ResourceController {
	
	@Autowired
	MovieFinderService movieFinderService;

	/**
	 * Dummy service to return a static response.
	 * 
	 * @return String
	 *
	 */
	@GetMapping(Constants.URI_PATH__PING)
	public String ping() {
		return Constants.PING_RESPONSE;
	}

	
	/**
	 * Movie Search API
	 * Get the movie title as path param and call the corresponding service class method in business layer..
	 * 
	 * @param String
	 * @return List<MovieBean>
	 *
	 */
	
	@GetMapping(Constants.URI_PATH__FINDER + "/{movieName}")
	public List<MovieBean> getTop10Movies(@PathVariable String movieName) throws JsonMappingException, JsonProcessingException {

		return movieFinderService.getTop10Movies(movieName);
	}
	
	
	
	/**
	 * Movie Rating API
	 * Get the movie rating (imdbID and rating) in the POST body 
	 * and call the corresponding service class method in business layer.
	 * 
	 * @param Rating
	 * @return Rating
	 *
	 */
	
	@PostMapping("/rating")
	public Rating rating(@RequestBody Rating rating) {

		return movieFinderService.rateingService(rating);
	}
	

}
