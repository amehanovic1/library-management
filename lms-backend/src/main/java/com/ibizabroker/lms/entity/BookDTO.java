package com.ibizabroker.lms.entity;

public class BookDTO {
    private Integer bookId;
    private String bookName;
    private Integer authorId;
    private String authorName;
    private Integer genreId;
    private String genreName;
    private Integer noOfCopies;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Integer getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(Integer noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public BookDTO() {
    }

    public BookDTO(Books book) {
        this.bookId = book.getBookId();
        this.bookName = book.getBookName();
        this.authorId = book.getAuthor().getAuthorId();
        this.authorName = book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName();
        this.genreId = book.getGenre().getGenreId();
        this.genreName = book.getGenre().getName();
        this.noOfCopies = book.getNoOfCopies();
    }
}
