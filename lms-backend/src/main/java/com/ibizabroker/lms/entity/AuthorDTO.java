package com.ibizabroker.lms.entity;

public class AuthorDTO {
    private Integer authorId;
    private String firstName;

    private String lastName;

    private String fullName;

    public AuthorDTO() {
    }

    public AuthorDTO(Author author) {
        this.authorId = author.getAuthorId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.fullName = author.getFirstName() + " " + author.getLastName();
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

