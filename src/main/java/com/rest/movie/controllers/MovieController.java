package com.rest.movie.controllers;

import com.rest.movie.dtos.MovieDTO;
import com.rest.movie.models.Movie;
import com.rest.movie.repositories.MovieRepository;
import com.rest.movie.services.MovieService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

    @GetMapping("/getAllMovies")
    @CrossOrigin
    @Cacheable("movies")
    public List<MovieDTO> getAllMovies() {
        logger.info("Fetching all movies");
        return movieService.getAllMovies();
    }

    @GetMapping("/getMovieById/{id}")
    @CrossOrigin
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        logger.info("Fetching movie by id");
        MovieDTO movieDTO = movieService.getMovieById(id);
        return movieDTO != null ? ResponseEntity.ok(movieDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/createMovie")
    @CacheEvict(value = "movies", allEntries = true)
    @CrossOrigin
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        logger.info("Creating new movie");
        MovieDTO savedMovieDTO = movieService.createMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovieDTO);
    }

    @DeleteMapping("/deleteMovie/{id}")
    @CrossOrigin
    @CacheEvict(value = "movies", allEntries = true)
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        logger.info("Deleting movie");
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateMovie/{id}")
    @CrossOrigin
    @CacheEvict(value = "movies", allEntries = true)
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieDTO movieDTO) {
        logger.info("Updating movie");
        MovieDTO updatedMovieDTO = movieService.updateMovie(id, movieDTO);
        return updatedMovieDTO != null ? ResponseEntity.ok(updatedMovieDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/voteMovie/{id}/{rating}")
    @CrossOrigin
    @CacheEvict(value = "movies", allEntries = true)
    public ResponseEntity<MovieDTO> voteMovie(@PathVariable Long id, @PathVariable double rating) {
        logger.info("Voting movie");
        MovieDTO updatedMovieDTO = movieService.voteMovie(id, rating);
        return updatedMovieDTO != null ? ResponseEntity.ok(updatedMovieDTO) : ResponseEntity.notFound().build();
    }
}
