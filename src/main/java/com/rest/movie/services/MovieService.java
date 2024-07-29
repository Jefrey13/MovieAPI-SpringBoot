package com.rest.movie.services;

import com.rest.movie.dtos.MovieDTO;
import com.rest.movie.models.Movie;
import com.rest.movie.repositories.MovieRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }

    public MovieDTO getMovieById(@NotNull Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(m -> modelMapper.map(m, MovieDTO.class)).orElse(null);
    }

    public MovieDTO createMovie(@Valid MovieDTO movieDTO) {
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        Movie savedMovie = movieRepository.save(movie);
        return modelMapper.map(savedMovie, MovieDTO.class);
    }

    public void deleteMovie(@NotNull Long id) {
        movieRepository.deleteById(id);
    }

    public MovieDTO updateMovie(@NotNull Long id, @Valid MovieDTO movieDTO) {
        if (!movieRepository.existsById(id)) {
            return null;
        }
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        movie.setId(id);
        Movie updatedMovie = movieRepository.save(movie);
        return modelMapper.map(updatedMovie, MovieDTO.class);
    }

    public MovieDTO voteMovie(@NotNull Long id, @NotNull double rating) {
        Optional<Movie> optional = movieRepository.findById(id);
        if (optional.isPresent()) {
            Movie movie = optional.get();
            double newRating = ((movie.getVotes() * movie.getRating()) + rating) / (movie.getVotes() + 1);
            movie.setVotes(movie.getVotes() + 1);
            movie.setRating(newRating);
            Movie savedMovie = movieRepository.save(movie);
            return modelMapper.map(savedMovie, MovieDTO.class);
        } else {
            return null;
        }
    }
}
