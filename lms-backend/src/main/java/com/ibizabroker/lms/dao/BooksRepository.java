package com.ibizabroker.lms.dao;

import com.ibizabroker.lms.entity.Author;
import com.ibizabroker.lms.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {

    Optional<Books> findByBookName(String name);
}
