package com.ibizabroker.lms.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer genreId;

    String name;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    List<Books> books;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }
}
