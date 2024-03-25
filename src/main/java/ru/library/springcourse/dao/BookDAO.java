package ru.library.springcourse.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.springcourse.models.Book;
import ru.library.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> allBooks() {
        log.info("Start method allBooks()");
        return jdbcTemplate.query("Select * from Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        log.info("Start method saveBook(), title is: " + book.getTitle());
        jdbcTemplate.update("INSERT INTO Book(person_id, title, year_of_realise, author) VALUES (?,?,?,?)",
                book.getPersonId(), book.getTitle(), book.getYearOfRealise(), book.getAuthor());
    }

    public Book show(int id) {
        log.info("Start method showBook, bookId is: " + id);
        return jdbcTemplate.query("Select * from Book where book_id=?", new BeanPropertyRowMapper<>(Book.class), new Object[]{id})
                .stream().findAny().orElse(null);
    }

    public Optional<Book> show(String title) {
        return jdbcTemplate.query("Select * From Book where title=?", new BeanPropertyRowMapper<>(Book.class), new Object[]{title})
                .stream().findAny();
    }

    public Optional<Person> bookIsUsedByPerson(int bookId) {
        log.info("Start method bookIsUsedByPerson(), bookId is: " + bookId);
        return jdbcTemplate.query(
                """
                            SELECT full_name FROM book JOIN person p
                            ON p.person_id = book.person_id
                            WHERE book_id = ?
                        """
                , new BeanPropertyRowMapper<>(Person.class), new Object[]{bookId}).stream().findAny();
    }

    public void makeBookFree(int bookId) {
        log.info("Start method makeBookFree(), bookId is: " + bookId);
        jdbcTemplate.update("UPDATE book SET person_id = NULL WHERE book_id = ?", bookId);
    }

    public void assignBook(int bookId, int personId) {
        log.info("Start method assignBook(), bookId is: " + bookId + "; personId is: " + personId);
        jdbcTemplate.update("UPDATE book SET person_id = ? where book_id = ?", personId, bookId);
    }

    public void delete(int id) {
        log.info("Start method delete for Book, bookId is: " + id);
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public void update(int id, Book updatedBook) {
        log.info("Start method update for Book, bookId is: " + id);
        jdbcTemplate.update("UPDATE Book SET title=?, year_of_realise=?, author=?, person_id=? WHERE book_id=?",
                updatedBook.getTitle(), updatedBook.getYearOfRealise(), updatedBook.getAuthor(), updatedBook.getPersonId(), id);
    }

}
