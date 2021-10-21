## Movie Search

* User can provide an movie title name (can be partial).
* Movie finder API accepsts the movie name in the path param.

```
	GET http://<HOSTNAME>/services/movies/finder/<Movie Title>
```
* Using the OMDB API, the top 10 search results are retrieved.
* The the movies are cross checked in the "academy_awards.csv" file to verify, if any of them has won any awards.
* If a movie has won an award, then all the awards along with the year and award category is returned.

```
Sample Response (for one movie)
	{
        "movieName": "Toy Story 3",
        "imdbID": "tt0435761",
        "poster": "https://m.media-amazon.com/images/M/MV5BMTgxOTY4Mjc0MF5BMl5BanBnXkFtZTcwNTA4MDQyMw@@._V1_SX300.jpg",
        "boxOffice": "$415,004,880",
        "awards": [
            {
                "year": "2010 (83rd)",
                "category": "Animated Feature Film"
            },
            {
                "year": "2010 (83rd)",
                "category": "Music (Song)"
            }
        ],
        "hasWon": "YES"
    }
```
* The response contains the movieName, imdbID, poster, boxOffice revenue, indication if Won any awards and if won, then the list of awards.
* The results are sorted based on box office revenue.



## Movie Rating

* The API allows to provide rating for a movie, based on the imdb ID.
* 5 star rating format is considered (minimum 1 and maximum 5).
* The client information is accepted in the POST body and the ratings for each movie is stored in a database, against the imdb ID.

```
	POST http://<HOSTNAME>/services/movies/rating
	
	POST Body
	{
		"imdbID": "tt0435761",
		"rating": 5
	}
	
```

* If the API transaction is successful, it returns a status code as 2000, imdb ID of the movie for whoch rating has been received, average rating based on all the submissions, total number of votes received for this movie.

```
	{
		"statusCode": 2000,
		"imdbID": "tt0435761",
		"rating": 3.0,
		"votingCount": 6
	}
```

* If the user provides a invalid rating, an error message is returned.

```
	{
		"statusCode": 5000,
		"message": "Rating should be between 1 to 5"
	}
```
* The ratings are stored in an external MySQL DB table.


## DB Table Structure (for Movie Rating)

```
* imdb ID :: String (Primary Key)
* Movie Name :: String (Currently not in use, Reserved for future implementation)
* Rating :: double (Average rating based on al the votings)
* Voting Count :: Total number of votes for the specific movie
```


## Security Considerations

* Both the movie search and rating API has been protected with OIDC implementation.
* Sothe client need to provide a valid Access (Bearer) Token to access the APIs.
* The API access is limited to tokens with "scope_movies_app_access" scope only.
* Access Token with 15 mins life time.
* A confidential client is registered in Key Cloak.
* For the demonstration (in attached postman collection), the ROPC grant is used. However the client supports the Authorization Code grant as well, but the implicit grant is disabled.
* The key cloak realm export has been provided.



## Deployment

* To test the service, the applications were deployed in Docker.
* The Dockerfile and docker-compose file has been provided.



## Tech Stack

```
* Spring boot, JPA
* JUnit
* MySQL DB
* Key Cloak
* Docker
```


## External Libraries

* OpenCSV - To read data from the awards CSV file (http://opencsv.sourceforge.net/)



