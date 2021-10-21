## Token Request (with ROPC)

Sample Request

```
POST http://localhost:8080/auth/realms/Backbase/protocol/openid-connect/token

POST Body
---------
grant_type:password
client_id:movies-finder
client_secret:4749c36b-efb2-400a-b5f0-6a123a92e28d
username:testuser
password:testuser

Headers
-------
Content-Type: application/x-www-form-urlencoded
Accept: application/json

```

Sample Response

```

{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJWZ2RQejF.....",
    "expires_in": 900,
    "refresh_expires_in": 1800,
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwZDRjYT.....",
    "token_type": "Bearer",
    "not-before-policy": 0,
    "session_state": "67fd0850-2ec6-49da-b41f-792c91f657c6",
    "scope": "profile scope_movies_app_access email"
}

```

## Movie Search

Sample Request

```
GET http://localhost:9090/services/movies/finder/Toy Story

Headers
-------
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJWZ2RQejF..... [Access Token]
Accept: application/json

```

Sample Response

```

[
    {
        "movieName": "Toy Story 4",
        "imdbID": "tt1979376",
        "poster": "https://m.media-amazon.com/images/M/MV5BMTYzMDM4NzkxOV5BMl5BanBnXkFtZTgwNzM1Mzg2NzM@._V1_SX300.jpg",
        "boxOffice": "$434,038,008",
        "hasWon": "NO"
    },
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
    },
    {
        "movieName": "Toy Story 2",
        "imdbID": "tt0120363",
        "poster": "https://m.media-amazon.com/images/M/MV5BMWM5ZDcxMTYtNTEyNS00MDRkLWI3YTItNThmMGExMWY4NDIwXkEyXkFqcGdeQXVyNjUwNzk3NDc@._V1_SX300.jpg",
        "boxOffice": "$245,852,179",
        "hasWon": "NO"
    },
	
    ........
	........
]

```


## Movie Rating

Sample Request

```
POST http://localhost:9090/services/movies/rating
	
POST Body
---------
{
	"imdbID": "tt0435761",
	"rating": 5
}

Headers
-------
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJWZ2RQejF..... [Access Token]
Accept: application/json
Content-Type: application/json

```

Sample Response

```
{
    "statusCode": 2000,
    "imdbID": "tt0435761",
    "rating": 4.4,
    "votingCount": 8
}
```
