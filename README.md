# FINAL-PROJECT
## EXPLANATION

## TITLE OF THE PROJECT: URL Shortener Services

## OBJECTIVE
- The goal of this project is to create a RESTful web service that shortens long URLs and allows users to use the short version to access the original one.
- It also tracks how many times each short URL is used.

## HOW IT WORKS?
- When a user sends a long URL to the application through an API request, the backend generates a random short code using a UUID - based logic.
- This short code is then stored in the database along with the original URL and a click count initialised to zero.
- When someone accesses the short URL, the application finds the corresponding original URL from the database, redirects the user to that URL, and increments the click count.

## WORKFLOW
- The user sends a long URL to the "/api/shorten" endpoint via a POST request.
- The application returns a short URL in the form of "/api/{shortCode}".
- When the user visits this short URL, the app redirects them to the original URL.
- Every access to the short URL is tracked by incrementing a counter in the database.

## COMPONENTS
- The main application launcher initialises the Spring Boot application.
- A model class(entity) defines fields for ID, original URL, short code, and click count.
- A repository interface handles interactions with the H2 database.
- A controller handles API endpoints for shortening URLs and redirecting based on short codes.

## DATABASE
- H2 : An in-memory database, which stores URL mappings and click counts during runtime.

## ENDPOINTS
- POST request to "/api/shorten" : Accepts a long URL and returns a shortened URL.
- GET request to "/api/{shortCode}" : Redirects the user to the original URL and updates the click count.

## FEATURES IMPLEMENTED
- Automatic generation of short URLs.
- Redirection from short URL to original long URL.
- Tracking how many times each short URL has been accessed.
- Database storage usinh H2 in-memory database.
- RESTful architecture using Spring Boot.
- API documentation via Swagger UI.

## TOOLS USED
- Java(Spring Boot framework)
- Spring Data JPA
- H2 Database
- Swagger UI for API testing
- Postman for request testing

## EXPECTED OUTPUT
- The user provides a long URL and receives a shortened version.
- When the short link is accessed, the application redirects the user and logs the access count.

