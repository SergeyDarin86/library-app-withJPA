package ru.library.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.library.springcourse.dao.BookDAO;
import ru.library.springcourse.models.Book;

@Component
public class BookValidator implements Validator {

    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookDAO.show(book.getTitle()).isPresent()) {
            if (bookDAO.show(book.getTitle()).get().getBookId() != book.getBookId()){
                errors.rejectValue("title", "", "This Book Title is used by someone");
            }
        }

    }

}
