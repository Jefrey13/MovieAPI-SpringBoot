package com.rest.movie.services;

import com.rest.movie.dtos.DirectorDTO;
import com.rest.movie.models.Director;
import com.rest.movie.repositories.DirectorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectorService {
    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DirectorDTO> getAllDirectors() {
        return directorRepository.findAll().stream()
                .map(director -> modelMapper.map(director, DirectorDTO.class))
                .collect(Collectors.toList());
    }

    public DirectorDTO getDirectorById(@NotNull Long id) {
        Optional<Director> director = directorRepository.findById(id);
        return director.map(d -> modelMapper.map(d, DirectorDTO.class)).orElse(null);
    }

    public DirectorDTO createDirector(@Valid DirectorDTO directorDTO) {
        Director director = modelMapper.map(directorDTO, Director.class);
        Director savedDirector = directorRepository.save(director);
        return modelMapper.map(savedDirector, DirectorDTO.class);
    }

    public void deleteDirector(@NotNull Long id) {
        directorRepository.deleteById(id);
    }

    public DirectorDTO updateDirector(@NotNull Long id, @Valid DirectorDTO directorDTO) {
        if (!directorRepository.existsById(id)) {
            return null;
        }
        Director director = modelMapper.map(directorDTO, Director.class);
        director.setId(id);
        Director updatedDirector = directorRepository.save(director);
        return modelMapper.map(updatedDirector, DirectorDTO.class);
    }
}
