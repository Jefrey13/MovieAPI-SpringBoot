# MovieAPI-SpringBoot

A comprehensive Movie API built with Spring Boot, featuring CRUD operations for movies, actors, directors, and genres. This project includes logging, custom error handling, DTO and DAO layers, service validation, caching, and Swagger documentation for API endpoints.

## Features

- CRUD operations for Movies, Actors, Directors, and Genres
- Custom error handling with `@ControllerAdvice` and custom exceptions
- DTO and DAO layers for clear separation of concerns
- Service layer with validation
- Caching with Spring Cache
- Logging with SLF4J and Logback
- Swagger documentation for API endpoints

## Prerequisites

- Java 21
- Maven
- MySQL or any other preferred database

## Getting Started

### Clone the repository

```bash
git clone https://github.com/yourusername/MovieAPI-SpringBoot.git
cd MovieAPI-SpringBoot 
```

## Configure the Database
Update the application.properties file with your database configuration:

**properties**
- spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabase
- spring.datasource.username=yourusername
- spring.datasource.password=yourpassword
- spring.jpa.hibernate.ddl-auto=update
- spring.jpa.show-sql=true

## Build the Project

```bash
mvn clean install
```
## Run the Application
```bash
mvn spring-boot:run
```
## Access Swagger UI

**Open your browser and navigate to http://localhost:8080/swagger-ui.html to access the Swagger documentation.**

### API Endpoints
1. Genre Controller 
   2. GET /api/v1/genres/getAllGenres 
   3. GET /api/v1/genres/getGenreById/{id} 
   4. POST /api/v1/genres/createGenre 
   5. PUT /api/v1/genres/updateGenre/{id} 
   6. DELETE /api/v1/genres/deleteGenre/{id}
2. Director Controller 
   3. GET /api/v1/directors/getAllDirectors 
   4. GET /api/v1/directors/getDirectorById/{id} 
   5. POST /api/v1/directors/createDirector 
   6. PUT /api/v1/directors/updateDirector/{id} 
   7. DELETE /api/v1/directors/deleteDirector/{id}
3. Actor Controller 
   4. GET /api/v1/actors/getAllActors 
   5. GET /api/v1/actors/getActorById/{id} 
   6. POST /api/v1/actors/createActor 
   7. PUT /api/v1/actors/updateActor/{id} 
   8. DELETE /api/v1/actors/deleteActor/{id}
4. Movie Controller 
   5. GET /api/v1/movies/getAllMovies 
   6. GET /api/v1/movies/getMovieById/{id} 
   7. POST /api/v1/movies/createMovie 
   8. PUT /api/v1/movies/updateMovie/{id} 
   9. DELETE /api/v1/movies/deleteMovie/{id} 
   10. GET /api/v1/movies/voteMovie/{id}/{rating}