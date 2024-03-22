package ru.library.springcourse.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.springcourse.models.Book;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;


    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> allBooks(){
        return jdbcTemplate.query("Select * from Book", new BeanPropertyRowMapper<>(Book.class));
    }

    // пока сохраняю из формы просто ID читателя
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(person_id, title, year_of_realise, author) VALUES (?,?,?,?)",
                book.getPersonId(), book.getTitle(), book.getYearOfRealise(), book.getAuthor());
    }

    public Book show(int id) {

        return jdbcTemplate.query("Select * from Book where book_id=?", new BeanPropertyRowMapper<>(Book.class), new Object[]{id})
                .stream().findAny().orElse(null);
    }
    public Optional<Book> show(String title) {
        return jdbcTemplate.query("Select * From Book where title=?", new BeanPropertyRowMapper<>(Book.class), new Object[]{title})
                .stream().findAny();
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET title=?, year_of_realise=?, author=?, person_id=? WHERE book_id=?",
                updatedBook.getTitle(), updatedBook.getYearOfRealise(), updatedBook.getAuthor(), updatedBook.getPersonId(), id);
    }

}
