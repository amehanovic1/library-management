package com.ibizabroker.lms.controller;


import com.ibizabroker.lms.dao.AuthorRepository;
import com.ibizabroker.lms.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:4200/")
@RestController
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDTO> authorDTOs = authors.stream()
                .map(AuthorDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(authorDTOs);
    }
}
