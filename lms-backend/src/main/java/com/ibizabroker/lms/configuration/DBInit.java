package com.ibizabroker.lms.configuration;


import com.ibizabroker.lms.dao.*;
import com.ibizabroker.lms.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class DBInit implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BooksRepository bookRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // 1. Provjeriti i popuniti tabele za Role
        if (roleRepository.count() == 0) {
            Role adminRole = new Role("Admin");
            Role staffRole = new Role("Staff");
            Role memberRole = new Role("Member");
            roleRepository.saveAll(Arrays.asList(adminRole, staffRole, memberRole));
        }

        // 2. Provjeriti i popuniti tabele za Users
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByRoleName("Admin")
                    .orElseThrow(() -> new RuntimeException("Role 'Admin' not found"));
            Role staffRole = roleRepository.findByRoleName("Staff")
                    .orElseThrow(() -> new RuntimeException("Role 'Staff' not found"));
            Role memberRole = roleRepository.findByRoleName("Member")
                    .orElseThrow(() -> new RuntimeException("Role 'Member' not found"));


            Users adminUser = new Users("admin", "Admin User", passwordEncoder.encode("admin123"), new HashSet<>(Arrays.asList(adminRole)));
            Users staffUser = new Users("staff", "Staff User", passwordEncoder.encode("staff123"), new HashSet<>(Arrays.asList(staffRole)));
            Users memberUser = new Users("member", "Member User", passwordEncoder.encode("member123"), new HashSet<>(Arrays.asList(memberRole)));
            userRepository.saveAll(Arrays.asList(adminUser, staffUser, memberUser));
        }

        // 3. Provjeriti i popuniti tabele za Author
        if (authorRepository.count() == 0) {
            Author author1 = new Author("Jane", "Austen");
            Author author2 = new Author("George", "Orwell");
            Author author3 = new Author("Mark", "Twain");
            authorRepository.saveAll(Arrays.asList(author1, author2, author3));
        }

        // 4. Provjeriti i popuniti tabele za Genre
        if (genreRepository.count() == 0) {
            Genre genre1 = new Genre("Fiction");
            Genre genre2 = new Genre("Science Fiction");
            Genre genre3 = new Genre("Adventure");
            genreRepository.saveAll(Arrays.asList(genre1, genre2, genre3));
        }

        // 5. Provjeriti i popuniti tabele za Books
        if (bookRepository.count() == 0) {
            // Prethodno moramo dohvatiti autore i žanrove jer bi mogli biti prazni ako se tabele nisu prethodno popunile
            Author author1 = authorRepository.findByFirstNameAndLastName("Jane", "Austen")
                    .orElseThrow(() -> new RuntimeException("Author 'Jane Austen' not found"));
            Author author2 = authorRepository.findByFirstNameAndLastName("George", "Orwell")
                    .orElseThrow(() -> new RuntimeException("Author 'George Orwell' not found"));
            Author author3 = authorRepository.findByFirstNameAndLastName("Mark", "Twain")
                    .orElseThrow(() -> new RuntimeException("Author 'Mark Twain' not found"));

            Genre genre1 = genreRepository.findByName("Fiction")
                    .orElseThrow(() -> new RuntimeException("Genre 'Fiction' not found"));
            Genre genre2 = genreRepository.findByName("Science Fiction")
                    .orElseThrow(() -> new RuntimeException("Genre 'Science Fiction' not found"));
            Genre genre3 = genreRepository.findByName("Adventure")
                    .orElseThrow(() -> new RuntimeException("Genre 'Adventure' not found"));

            Books book1 = new Books();
            book1.setBookName("Pride and Prejudice");
            book1.setAuthor(author1);
            book1.setGenre(genre1);
            book1.setNoOfCopies(5);

            Books book2 = new Books();
            book2.setBookName("1984");
            book2.setAuthor(author2);
            book2.setGenre(genre2);
            book2.setNoOfCopies(10);

            Books book3 = new Books();
            book3.setBookName("Adventures of Huckleberry Finn");
            book3.setAuthor(author3);
            book3.setGenre(genre3);
            book3.setNoOfCopies(7);

            bookRepository.saveAll(Arrays.asList(book1, book2, book3));
        }

        // 6. Provjeriti i popuniti tabele za Borrow
        if (borrowRepository.count() == 0) {
            // Prethodno moramo dohvatiti korisnike i knjige
            Users memberUser = userRepository.findByUsername("member").orElseThrow(() -> new RuntimeException("User with username 'member' not found"));;
            Users staffUser = userRepository.findByUsername("staff").orElseThrow(() -> new RuntimeException("User with username 'member' not found"));;

            Books book1 = bookRepository.findByBookName("Pride and Prejudice")
                    .orElseThrow(() -> new RuntimeException("Book 'Pride and Prejudice' not found"));
            Books book2 = bookRepository.findByBookName("1984")
                    .orElseThrow(() -> new RuntimeException("Book '1984' not found"));

            Borrow borrow1 = new Borrow();
            borrow1.setBookId(book1.getBookId());
            borrow1.setUserId(memberUser.getUserId());
            borrow1.setIssueDate(new Date());
            borrow1.setDueDate(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000)); // +7 dana
            borrow1.setReturnDate(null);

            Borrow borrow2 = new Borrow();
            borrow2.setBookId(book2.getBookId());
            borrow2.setUserId(staffUser.getUserId());
            borrow2.setIssueDate(new Date());
            borrow2.setDueDate(new Date(System.currentTimeMillis() + 14L * 24 * 60 * 60 * 1000)); // +14 dana
            borrow2.setReturnDate(null);

            borrowRepository.saveAll(Arrays.asList(borrow1, borrow2));
        }

        System.out.println("Inicijalni podaci uspješno uneseni u bazu.");
    }

}

