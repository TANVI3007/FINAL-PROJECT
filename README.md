# FINAL-PROJECT-1   (URL Shortener Services)
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

## FINAL CODE
// URLShortenerApplication.java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class URLShortenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(URLShortenerApplication.class, args);
    }
}

// URLMapping.java
import jakarta.persistence.*;

@Entity
public class URLMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;
    private String shortCode;
    private int clickCount = 0;

    public Long getId() { return id; }
    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
    public String getShortCode() { return shortCode; }
    public void setShortCode(String shortCode) { this.shortCode = shortCode; }
    public int getClickCount() { return clickCount; }
    public void setClickCount(int clickCount) { this.clickCount = clickCount; }
}

// URLRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface URLRepository extends JpaRepository<URLMapping, Long> {
    Optional<URLMapping> findByShortCode(String shortCode);
}

// URLController.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping("/api")
public class URLController {
    @Autowired
    private URLRepository urlRepository;
    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody String originalUrl) {
        String shortCode = UUID.randomUUID().toString().substring(0, 6);
        URLMapping mapping = new URLMapping();
        mapping.setOriginalUrl(originalUrl);
        mapping.setShortCode(shortCode);
        urlRepository.save(mapping);
        return "http://localhost:8080/api/" + shortCode;
    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortCode) {
        Optional<URLMapping> mapping = urlRepository.findByShortCode(shortCode);
        if (mapping.isPresent()) {
            URLMapping map = mapping.get();
            map.setClickCount(map.getClickCount() + 1);
            urlRepository.save(map);
            return ResponseEntity.status(302).location(URI.create(map.getOriginalUrl())).build();
        }
        return ResponseEntity.notFound().build();
    }
}
# FINAL PROJECT-2    (Job Portal System)
## EXPLANATION

## TITLE OF THE PROJECT: Job Poratl System


