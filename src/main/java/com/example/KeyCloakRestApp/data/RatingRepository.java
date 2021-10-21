/**
 * 
 */
package com.example.KeyCloakRestApp.data;

import org.springframework.data.repository.CrudRepository;

import com.example.KeyCloakRestApp.bean.Rating;

/**
 * @author biswajit
 *
 */

/**
 * Repository interface to interact with the Database.
 * Custom implementation findByMovieName(String movieName) is provided to search movie by its name.
 */

public interface RatingRepository extends CrudRepository<Rating, String> {
	
	public Rating findByMovieName(String movieName);
	
}
