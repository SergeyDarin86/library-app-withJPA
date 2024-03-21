package ru.library.springcourse.models;

import javax.validation.constraints.*;

public class Book {

    private int bookId;

    @NotNull(message = "Year of Realise should not be empty")
//    @Pattern(regexp = "\\d{4}",message = "The format has to be: (4 numbers)")
    private int yearOfRealise;

    @NotEmpty(message = "Title of Book should not be empty")
    @Size(min = 1, max = 100, message = "Title should be between 1 and 100 characters")
    @Pattern(regexp = "[А-ЯЁ].+")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 5, max = 50, message = "Author should be between 5 and 50 characters")
    @Pattern(regexp = "[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+", message = "The Author should have the format: Surname Name")
    private String author;

//    @NotNull(message = "Person id should not be empty")
    private Integer personId;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getYearOfRealise() {
        return yearOfRealise;
    }

    public void setYearOfRealise(int yearOfRealise) {
        this.yearOfRealise = yearOfRealise;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}
