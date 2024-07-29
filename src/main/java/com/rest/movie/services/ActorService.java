package com.rest.movie.services;

import com.rest.movie.dtos.ActorDTO;
import com.rest.movie.exceptions.ResourceNotFoundException;
import com.rest.movie.models.Actor;
import com.rest.movie.repositories.ActorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActorService {
    private static final Logger logger = LoggerFactory.getLogger(ActorService.class);

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ActorDTO> getAllActors() {
        try {
            return actorRepository.findAll().stream()
                    .map(actor -> modelMapper.map(actor, ActorDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all actors", e);
            throw new RuntimeException("Error fetching all actors", e);
        }
    }

    public Optional<ActorDTO> getActorById(Long id) {
        try {
            return actorRepository.findById(id)
                    .map(actor -> modelMapper.map(actor, ActorDTO.class));
        } catch (Exception e) {
            logger.error("Error fetching actor by id {}", id, e);
            throw new RuntimeException("Error fetching actor by id " + id, e);
        }
    }

    public ActorDTO createActor(ActorDTO actorDTO) {
        try {
            Actor actor = modelMapper.map(actorDTO, Actor.class);
            Actor savedActor = actorRepository.save(actor);
            return modelMapper.map(savedActor, ActorDTO.class);
        } catch (Exception e) {
            logger.error("Error creating actor with name {}", actorDTO.getName(), e);
            throw new RuntimeException("Error creating actor", e);
        }
    }

    public void deleteActor(Long id) {
        try {
            if (!actorRepository.existsById(id)) {
                throw new ResourceNotFoundException("Actor not found with id " + id);
            }
            actorRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting actor with id {}", id, e);
            throw new RuntimeException("Error deleting actor with id " + id, e);
        }
    }

    public ActorDTO updateActor(Long id, ActorDTO actorDTO) {
        try {
            if (!actorRepository.existsById(id)) {
                throw new ResourceNotFoundException("Actor not found with id " + id);
            }
            Actor actor = modelMapper.map(actorDTO, Actor.class);
            actor.setId(id);
            Actor updatedActor = actorRepository.save(actor);
            return modelMapper.map(updatedActor, ActorDTO.class);
        } catch (Exception e) {
            logger.error("Error updating actor with id {}", id, e);
            throw new RuntimeException("Error updating actor with id " + id, e);
        }
    }

    public boolean existsById(Long id) {
        try {
            return actorRepository.existsById(id);
        } catch (Exception e) {
            logger.error("Error checking existence of actor with id {}", id, e);
            throw new RuntimeException("Error checking existence of actor with id " + id, e);
        }
    }
}