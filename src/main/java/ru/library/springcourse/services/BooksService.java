package ru.library.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.library.springcourse.models.Book;
import ru.library.springcourse.models.Person;
import ru.library.springcourse.repositories.BooksRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book>findAllBooksByPerson(Person person){
        return booksRepository.findAllByPerson(person);
    }

}
