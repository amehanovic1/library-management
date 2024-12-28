package com.ibizabroker.lms.dao;


import com.ibizabroker.lms.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Optional<Author> findByFirstName(String firstName);
    Optional<Author> findByLastName(String lastName);

    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
