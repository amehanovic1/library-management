package com.ibizabroker.lms.controller;


import com.ibizabroker.lms.dao.AuthorRepository;
import com.ibizabroker.lms.dao.GenreRepository;
import com.ibizabroker.lms.entity.Author;
import com.ibizabroker.lms.entity.Genre;
import com.ibizabroker.lms.entity.GenreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:4200/")
@RestController
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/genres")
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        List<GenreDTO> genreDTOs = genres.stream()
                .map(GenreDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(genreDTOs);
    }
}
