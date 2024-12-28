package com.ibizabroker.lms.entity;

public class GenreDTO {
    private Integer genreId;
    private String name;

    public GenreDTO() {
    }

    public GenreDTO(Genre genre) {
        this.genreId = genre.getGenreId();
        this.name = genre.getName();
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
}
