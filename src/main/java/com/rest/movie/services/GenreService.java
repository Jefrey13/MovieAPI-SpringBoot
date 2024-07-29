package com.rest.movie.services;

import com.rest.movie.dtos.GenreDTO;
import com.rest.movie.models.Genre;
import com.rest.movie.repositories.GenreRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<GenreDTO> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(genre -> modelMapper.map(genre, GenreDTO.class))
                .collect(Collectors.toList());
    }

    public GenreDTO getGenreById(@NotNull Long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.map(g -> modelMapper.map(g, GenreDTO.class)).orElse(null);
    }

    public GenreDTO createGenre(@Valid GenreDTO genreDTO) {
        Genre genre = modelMapper.map(genreDTO, Genre.class);
        Genre savedGenre = genreRepository.save(genre);
        return modelMapper.map(savedGenre, GenreDTO.class);
    }

    public void deleteGenre(@NotNull Long id) {
        genreRepository.deleteById(id);
    }

    public GenreDTO updateGenre(@NotNull Long id, @Valid GenreDTO genreDTO) {
        if (!genreRepository.existsById(id)) {
            return null;
        }
        Genre genre = modelMapper.map(genreDTO, Genre.class);
        genre.setId(id);
        Genre updatedGenre = genreRepository.save(genre);
        return modelMapper.map(updatedGenre, GenreDTO.class);
    }
}
