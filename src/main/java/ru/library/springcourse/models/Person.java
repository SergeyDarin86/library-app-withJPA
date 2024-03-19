package ru.library.springcourse.models;

import javax.validation.constraints.*;

public class Person {

    private int id;

    @NotEmpty(message = "FullName should not be empty")
    @Size(min = 8, max = 100, message = "The FullName should be between 8 and 100 characters")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+", message = "The FullName should have the format: Surname Name Patronymic")
    private String fullName;

    @NotEmpty(message = "Year of Birthday should not be empty")
    @Pattern(regexp = "\\d{4}",message = "The format has to be: (4 numbers)")
    @Max(value = 2018, message = "The person has to be born at least in 2018 year")
    @Min(value = 1900, message = "Year of Birthday should be more than 1900")
    private int yearOfBirthday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirthday() {
        return yearOfBirthday;
    }

    public void setYearOfBirthday(int yearOfBirthday) {
        this.yearOfBirthday = yearOfBirthday;
    }
}
