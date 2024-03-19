package ru.library.springcourse.models;

import javax.validation.constraints.*;

public class Book {

    private int bookId;

    @NotEmpty(message = "Year of Realise should not be empty")
    @Pattern(regexp = "\\d{4}",message = "The format has to be: (4 numbers)")
    private String yearOfRealise;

    @NotEmpty(message = "Title of Book should not be empty")
    @Size(min = 1, max = 100, message = "Title should be between 1 and 100 characters")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 8, max = 50, message = "Author should be between 8 and 50 characters")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "The Author should have the format: Surname Name")
    private String author;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getYearOfRealise() {
        return yearOfRealise;
    }

    public void setYearOfRealise(String yearOfRealise) {
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
}
