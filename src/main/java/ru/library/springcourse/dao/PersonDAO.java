package ru.library.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.springcourse.models.Book;
import ru.library.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

//@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> allReaders() {
        return jdbcTemplate.query("Select * from Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int personId) {

        return jdbcTemplate.query("Select * from Person where person_id=?", new BeanPropertyRowMapper<>(Person.class), new Object[]{personId})
                .stream().findAny().orElse(null);
    }

    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query("Select * From Person where full_name=?", new BeanPropertyRowMapper<>(Person.class), new Object[]{fullName})
                .stream().findAny();
    }

    //TODO: данный запрос можно сделать проще
    // просто сделать select из Book (без JOIN)
    public List<Book> showBookList(int person_id) {
        return jdbcTemplate.query(
                """
                        Select * FROM person LEFT JOIN Book
                        on person.person_id = Book.person_id
                        where Book.person_id=?
                    """
                , new BeanPropertyRowMapper<>(Book.class), new Object[]{person_id}).stream().toList();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, year_of_birthday) VALUES (?,?)",
                person.getFullName(), person.getYearOfBirthday());
    }

    public void update(int id, Person updatedPerson) {
        System.out.println(id + " id || " + updatedPerson.getPersonId() + " <-- updatedPerson from DAO");
        jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birthday=? WHERE person_id=?",
                updatedPerson.getFullName(), updatedPerson.getYearOfBirthday(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }

}
