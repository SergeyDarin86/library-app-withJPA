package ru.library.springcourse.models;

import javax.validation.constraints.*;

public class Book {

    private int bookId;

    @NotNull(message = "Year of Realise should not be empty")
    @Min(value = 1, message = "Year of Realise should be more than 0")
//    @Pattern(regexp = "\\d{4}",message = "The format has to be: (4 numbers)")

    private Integer yearOfRealise;

    @NotEmpty(message = "Title of Book should not be empty")
    @Size(min = 1, max = 100, message = "Title should be between 1 and 100 characters")
    @Pattern(regexp = "[А-ЯЁ].+", message = "Title book should have format: Apple")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 5, max = 50, message = "Author should be between 5 and 50 characters")
    @Pattern(regexp = "[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+", message = "The Author should have the format: Surname Name")
    private String author;

    private Integer personId;

    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Integer getYearOfRealise() {
        return yearOfRealise;
    }

    public void setYearOfRealise(Integer yearOfRealise) {
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
