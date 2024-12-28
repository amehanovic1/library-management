package com.ibizabroker.lms.controller;

import com.ibizabroker.lms.dao.AuthorRepository;
import com.ibizabroker.lms.dao.BooksRepository;
import com.ibizabroker.lms.dao.GenreRepository;
import com.ibizabroker.lms.entity.Author;
import com.ibizabroker.lms.entity.BookDTO;
import com.ibizabroker.lms.entity.Books;
import com.ibizabroker.lms.entity.Genre;
import com.ibizabroker.lms.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class BooksController {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/books")
    public List<BookDTO> getAllBooks(){
        List<Books> books = booksRepository.findAll();
        return books.stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/books-user")
    public List<BookDTO> getAllBooksForUser(){
        List<Books> books = booksRepository.findAll();
        return books.stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Integer id) {
        Books book = booksRepository.findById(id).orElseThrow(() -> new NotFoundException("Book with id "+ id +" does not exist."));
        BookDTO bookDTO = new BookDTO(book);
        return ResponseEntity.ok(bookDTO);
    }

    @PreAuthorize("hasRole('Admin') or hasRole('Staff')")
    @PostMapping("/books")
    public ResponseEntity<BookDTO> createBook(@RequestBody Books book) {
        Author author = authorRepository.findById(book.getAuthor().getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author not found"));

        Genre genre = genreRepository.findById(book.getGenre().getGenreId())
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        book.setAuthor(author);
        book.setGenre(genre);


        Books savedBook = booksRepository.save(book);
        BookDTO bookDTO = new BookDTO(savedBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
    }

    @PreAuthorize("hasRole('Admin') or hasRole('Staff')")
    @PutMapping("/books/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Integer id, @RequestBody Books bookDetails) {
        Books book = booksRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " does not exist."));

        if (bookDetails.getAuthor() == null || bookDetails.getAuthor().getAuthorId() == null) {
            throw new IllegalArgumentException("Author ID must not be null");
        }

        if (bookDetails.getGenre() == null || bookDetails.getGenre().getGenreId() == null) {
            throw new IllegalArgumentException("Genre ID must not be null");
        }

        Author author = authorRepository.findById(bookDetails.getAuthor().getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author not found"));

        Genre genre = genreRepository.findById(bookDetails.getGenre().getGenreId())
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        book.setBookName(bookDetails.getBookName());
        book.setAuthor(author);
        book.setGenre(genre);
        book.setNoOfCopies(bookDetails.getNoOfCopies());

        Books updatedBook = booksRepository.save(book);

        BookDTO updatedBookDTO = new BookDTO(updatedBook);

        return ResponseEntity.ok(updatedBookDTO);
    }


    @PreAuthorize("hasRole('Admin') or hasRole('Staff')")
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable Integer id) {
        Books book = booksRepository.findById(id).orElseThrow(() -> new NotFoundException("Book with id "+ id +" does not exist."));

        booksRepository.delete(book);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('Admin') or hasRole('Staff')")
    @GetMapping("/books/name/{id}")
    public ResponseEntity<String> getBookNameById(@PathVariable Integer id) {
        Books book = booksRepository.findById(id).orElseThrow(() -> new NotFoundException("Book with id " + id + " does not exist."));
        return ResponseEntity.ok(book.getBookName());
    }
}
