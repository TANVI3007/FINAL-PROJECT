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

## OBJECTIVE:
- To build a web-based job portal that allows employers to post job openings and applicants to search, apply, and track their application status. The system implements role-based access using Spring Security.

## HOW IT WORKS:
- Employers register and log in to post job listings.
- Applicants register and log in to search and apply for jobs.
- Applications are stored and tracked with their status (APPLIED, ACCEPTED, REJECTED).
- All user and job data is persisted in a MySQL database.

## WORKFLOW:
- User registers as an EMPLOYER or APPLICANT.
- EMPLOYER logs in and posts job listings.
- APPLICANT logs in, browses jobs, and applies.
- EMPLOYER can view applicants and update status.
- APPLICANT can check the status of their applications.

## COMPONENTS:
- Spring Boot Web App
- MySQL Database
- Spring Security (Authentication/Authorization)
- Thymeleaf Frontend Templates
- REST API Controllers for Jobs and Applications

## DATABASE:
- Table: users (id, username, password, role)
- Table: jobs (id, title, description, location, employer_id)
- Table: applications (id, applicant_id, job_id, status)

## ENDPOINTS:
- /register (GET/POST): User registration
- /login (GET/POST): Login page
- /jobs/new: Form for employer to post jobs
- /jobs: View all jobs (applicants)
- /apply/{jobId}: Apply for a job
- /applications: View applications by applicant

## FEATURES IMPLEMENTED:
- Role-based access control
- Job posting and search
- Job application with status tracking
- Search by job title or location
- Persistent data with JPA and MySQL

## TOOLS USED:
- Java 17+
- Spring Boot (Web, JPA, Security)
- MySQL
- Thymeleaf
- IntelliJ IDEA / VS Code
- Postman (API testing)

## EXPECTED OUTPUT:
- EMPLOYER can add and manage job listings.
- APPLICANT can view, search, and apply for jobs.
- User-friendly web interface for both roles.
- Application status updates tracked in the system.

## FINAL CODE

// File: JobPortalApplication.java
package com.example.jobportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobPortalApplication.class, args);
    }
}

// File: entity/User.java
package com.example.jobportal.entity;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        EMPLOYER, APPLICANT
    }

    // Getters and Setters
}

// File: entity/Job.java
package com.example.jobportal.entity;

import jakarta.persistence.*;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String location;

    @ManyToOne
    private User employer;

    // Getters and Setters
}

// File: entity/Application.java
package com.example.jobportal.entity;

import jakarta.persistence.*;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User applicant;

    @ManyToOne
    private Job job;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        APPLIED, ACCEPTED, REJECTED
    }

    // Getters and Setters
}

// File: repository/UserRepository.java
package com.example.jobportal.repository;

import com.example.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

// File: repository/JobRepository.java
package com.example.jobportal.repository;

import com.example.jobportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByTitleContainingOrLocationContaining(String title, String location);
}

// File: repository/ApplicationRepository.java
package com.example.jobportal.repository;

import com.example.jobportal.entity.Application;
import com.example.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByApplicant(User user);
}

// File: config/SecurityConfig.java
package com.example.jobportal.config;

import com.example.jobportal.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/jobs/new").hasRole("EMPLOYER")
            .antMatchers("/apply/**", "/jobs/**").authenticated()
            .anyRequest().permitAll()
            .and().formLogin().defaultSuccessUrl("/", true)
            .and().logout();
    }
}

// File: service/UserService.java
package com.example.jobportal.service;

import com.example.jobportal.entity.User;
import com.example.jobportal.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}

// Add controllers and Thymeleaf templates as needed
