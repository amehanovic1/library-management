package com.ibizabroker.lms.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer authorId;

    String firstName;
    String lastName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<Books> books;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author() {
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }
}
