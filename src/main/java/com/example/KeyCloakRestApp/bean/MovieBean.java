package com.example.KeyCloakRestApp.bean;

import java.util.List;

import com.example.KeyCloakRestApp.util.Util;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MovieBean implements Comparable<MovieBean> {
	
	private String year;
	private String category;
	private String nominee;
	private String additionalInfo;
	private String hasWonAwards;
	private String movieName;
	private String imdbID;
	private String poster;
	private String boxOffice;
	private List<Award> awards;
	
	public MovieBean() {
		// TODO Auto-generated constructor stub
	}
	
	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getBoxOffice() {
		return boxOffice;
	}

	public void setBoxOffice(String boxOffice) {
		this.boxOffice = boxOffice;
	}
	
	public MovieBean(String movieName) {
		this.movieName = movieName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNominee() {
		return nominee;
	}

	public void setNominee(String nominee) {
		this.nominee = nominee;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getHasWon() {
		return hasWonAwards;
	}

	public void setHasWon(String hasWon) {
		this.hasWonAwards = hasWon;
	}

	public List<Award> getAwards() {
		return awards;
	}

	public void setAwards(List<Award> awards) {
		this.awards = awards;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	@Override
	public int compareTo(MovieBean o) {

		return (int) (Util.formatNumber(o.getBoxOffice()) - Util.formatNumber(this.getBoxOffice()));
	}

}
