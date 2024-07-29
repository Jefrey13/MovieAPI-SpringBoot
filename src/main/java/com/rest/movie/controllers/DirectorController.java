package com.rest.movie.controllers;
import com.rest.movie.dtos.DirectorDTO;
import com.rest.movie.models.Director;
import com.rest.movie.repositories.DirectorRepository;
import com.rest.movie.services.DirectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/directors")
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    @GetMapping("/getAllDirectors")
    @CrossOrigin
    public List<DirectorDTO> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/getDirectorById/{id}")
    @CrossOrigin
    public ResponseEntity<DirectorDTO> getDirectorById(@PathVariable Long id) {
        DirectorDTO directorDTO = directorService.getDirectorById(id);
        return directorDTO != null ? ResponseEntity.ok(directorDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/createDirector")
    @CrossOrigin
    public ResponseEntity<DirectorDTO> createDirector(@Valid @RequestBody DirectorDTO directorDTO) {
        DirectorDTO savedDirectorDTO = directorService.createDirector(directorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDirectorDTO);
    }

    @DeleteMapping("/deleteDirector/{id}")
    @CrossOrigin
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateDirector/{id}")
    @CrossOrigin
    public ResponseEntity<DirectorDTO> updateDirector(@PathVariable Long id, @Valid @RequestBody DirectorDTO directorDTO) {
        DirectorDTO updatedDirectorDTO = directorService.updateDirector(id, directorDTO);
        return updatedDirectorDTO != null ? ResponseEntity.ok(updatedDirectorDTO) : ResponseEntity.notFound().build();
    }
}