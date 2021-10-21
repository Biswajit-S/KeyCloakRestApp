package com.example.KeyCloakRestApp.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//@JsonInclude(Include.NON_NULL)
@JsonInclude(Include.NON_DEFAULT)
@Entity
public class Rating extends StatusMessage {
	
	@Id
	private String imdbID;
	private String movieName;
	private double rating;
	private int votingCount;
	
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getImdbID() {
		return imdbID;
	}
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getVotingCount() {
		return votingCount;
	}
	public void setVotingCount(int votingCount) {
		this.votingCount = votingCount;
	}

}
