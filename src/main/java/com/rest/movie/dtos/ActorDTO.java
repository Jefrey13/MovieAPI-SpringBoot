package com.rest.movie.dtos;

import jakarta.validation.constraints.NotEmpty;

public class ActorDTO {
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
