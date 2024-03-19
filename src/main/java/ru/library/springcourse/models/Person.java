package ru.library.springcourse.models;

import javax.validation.constraints.*;

public class Person {

    //!!! Быть внимательным к именованию полей сущностей!!!
    // если в базе поле записано как person_id, то в сущности лучше именовать с использованием Camel
    // и дать название для поля как personId, а не просто "id"
    // в противном случае не получается полноценно извлечь значение поля и оно для всех будет равно 0
    // долго искал причину, почему приходит из запроса "Select * from Person" значения для id для всех
    // записей было равно 0 (((
    private int personId;

    @NotEmpty(message = "FullName should not be empty")
    @Size(min = 8, max = 100, message = "The FullName should be between 8 and 100 characters")
//    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+", message = "The FullName should have the format: Surname Name Patronymic")
    private String fullName;

//    @Pattern(regexp = "\\d{4}",message = "The format has to be: (4 numbers)")
//    @Max(value = 2018, message = "The person has to be born at least in 2018 year")
    @Min(value = 1900, message = "Year of Birthday should be more than 1900")
    @NotNull(message = "not null")
    private int yearOfBirthday;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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
