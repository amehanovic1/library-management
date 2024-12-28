package com.ibizabroker.lms.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer bookId;
    String bookName;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    Author author;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    Genre genre;

    Integer noOfCopies;

    public void borrowBook() {
        this.noOfCopies--;
    }

    public void returnBook() {
        this.noOfCopies++;
    }

    public Books() {
    }
}
