package com.rest.movie.controllers;
import com.rest.movie.dtos.GenreDTO;
import com.rest.movie.models.Genre;
import com.rest.movie.repositories.GenreRepository;
import com.rest.movie.services.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/getAllGenres")
    @CrossOrigin
    public List<GenreDTO> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/getGenreById/{id}")
    @CrossOrigin
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable Long id) {
        GenreDTO genreDTO = genreService.getGenreById(id);
        return genreDTO != null ? ResponseEntity.ok(genreDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/createGenre")
    @CrossOrigin
    public ResponseEntity<GenreDTO> createGenre(@Valid @RequestBody GenreDTO genreDTO) {
        GenreDTO savedGenreDTO = genreService.createGenre(genreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGenreDTO);
    }

    @DeleteMapping("/deleteGenre/{id}")
    @CrossOrigin
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateGenre/{id}")
    @CrossOrigin
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable Long id, @Valid @RequestBody GenreDTO genreDTO) {
        GenreDTO updatedGenreDTO = genreService.updateGenre(id, genreDTO);
        return updatedGenreDTO != null ? ResponseEntity.ok(updatedGenreDTO) : ResponseEntity.notFound().build();
    }
}
