package com.rest.movie.controllers;
import com.rest.movie.dtos.ActorDTO;
import com.rest.movie.models.Actor;
import com.rest.movie.repositories.ActorRepository;
import com.rest.movie.services.ActorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/actors")
public class ActorController {
    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

    @Autowired
    private ActorService actorService;

    @GetMapping("/getAllActors")
    @CrossOrigin
    public ResponseEntity<List<ActorDTO>> getAllActors() {
        logger.info("Fetching all actors");
        try {
            return ResponseEntity.ok(actorService.getAllActors());
        } catch (RuntimeException e) {
            logger.error("Error fetching all actors", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getActorById/{id}")
    @CrossOrigin
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Long id) {
        logger.debug("Fetching actor with id {}", id);
        try {
            Optional<ActorDTO> actor = actorService.getActorById(id);
            if (actor.isPresent()) {
                return ResponseEntity.ok(actor.get());
            } else {
                logger.warn("Actor with id {} not found", id);
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            logger.error("Error fetching actor by id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/createActor")
    @CrossOrigin
    public ResponseEntity<ActorDTO> createActor(@Valid @RequestBody ActorDTO actorDTO) {
        logger.info("Creating new actor with name {}", actorDTO.getName());
        try {
            ActorDTO savedActor = actorService.createActor(actorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedActor);
        } catch (RuntimeException e) {
            logger.error("Error creating actor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteActor/{id}")
    @CrossOrigin
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        logger.info("Deleting actor with id {}", id);
        try {
            if (!actorService.existsById(id)) {
                logger.warn("Attempt to delete non-existent actor with id {}", id);
                return ResponseEntity.notFound().build();
            }
            actorService.deleteActor(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Error deleting actor with id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateActor/{id}")
    @CrossOrigin
    public ResponseEntity<ActorDTO> updateActor(@PathVariable Long id, @Valid @RequestBody ActorDTO updatedActor) {
        logger.info("Updating actor with id {}", id);
        try {
            if (!actorService.existsById(id)) {
                logger.warn("Attempt to update non-existent actor with id {}", id);
                return ResponseEntity.notFound().build();
            }
            ActorDTO savedActor = actorService.updateActor(id, updatedActor);
            return ResponseEntity.ok(savedActor);
        } catch (RuntimeException e) {
            logger.error("Error updating actor with id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
