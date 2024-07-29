package com.rest.movie.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class MovieDTO {
    private Long id;

    @NotEmpty(message = "Title should not be empty")
    private String title;
    @Min(value = 1888, message = "Year should not be less than 1888")
    private int year;
    @Min(value = 0, message = "Votes should not be negative")
    private int votes;
    @Min(value = 0, message = "Rating should be between 0 and 10")
    @Max(value = 10, message = "Rating should be between 0 and 10")
    private double rating;
    private String imageUrl;
    @NotNull(message = "Director should not be null")
    private DirectorDTO director;
    private Set<GenreDTO> genres;
    private Set<ActorDTO> actors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public DirectorDTO getDirector() {
        return director;
    }

    public void setDirector(DirectorDTO director) {
        this.director = director;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDTO> genres) {
        this.genres = genres;
    }

    public Set<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(Set<ActorDTO> actors) {
        this.actors = actors;
    }
}
