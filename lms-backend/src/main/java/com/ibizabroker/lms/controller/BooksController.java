package com.ibizabroker.lms.controller;

import com.ibizabroker.lms.dao.AuthorRepository;
import com.ibizabroker.lms.dao.BooksRepository;
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

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/admin")
public class BooksController {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorRepository authorRepository;

    

    @GetMapping("/books")
    public List<BookDTO> getAllBooks(){
        List<Books> books = booksRepository.findAll();
        return books.stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Integer id) {
        Books book = booksRepository.findById(id).orElseThrow(() -> new NotFoundException("Book with id "+ id +" does not exist."));
        BookDTO bookDTO = new BookDTO(book);
        return ResponseEntity.ok(bookDTO);
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/books")
    public ResponseEntity<BookDTO> createBook(@RequestBody Books book) {
        Author author = authorRepository.findById(book.getAuthor().getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author not found"));

        Genre genre = genreRepository.findById(book.getGenre().getGenreId())
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        // Povezivanje sa knjigom
        book.setAuthor(author);
        book.setGenre(genre);


        Books savedBook = booksRepository.save(book);
        BookDTO bookDTO = new BookDTO(savedBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/books/{id}")
    public ResponseEntity<Books> updateBook(@PathVariable Integer id, @RequestBody Books bookDetails) {
        Books book = booksRepository.findById(id).orElseThrow(() -> new NotFoundException("Book with id "+ id +" does not exist."));

        book.setBookName(bookDetails.getBookName());
        book.setAuthor(bookDetails.getAuthor());
        book.setGenre(bookDetails.getGenre());
        book.setNoOfCopies(bookDetails.getNoOfCopies());

        Books updatedBook = booksRepository.save(book);
        return ResponseEntity.ok(updatedBook);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable Integer id) {
        Books book = booksRepository.findById(id).orElseThrow(() -> new NotFoundException("Book with id "+ id +" does not exist."));

        booksRepository.delete(book);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
