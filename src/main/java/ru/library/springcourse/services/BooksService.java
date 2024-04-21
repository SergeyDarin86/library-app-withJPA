package ru.library.springcourse.services;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.library.springcourse.models.Book;
import ru.library.springcourse.models.Person;
import ru.library.springcourse.repositories.BooksRepository;
import ru.library.springcourse.repositories.PeopleRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    private final EntityManager entityManager;

    private final PeopleRepository peopleRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository, EntityManager entityManager, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.entityManager = entityManager;
        this.peopleRepository = peopleRepository;
    }

    public List<Book>findAllBooksByPerson(Person person){
        return booksRepository.findAllByPerson(person);
    }

    public List<Book>findAll(Integer page, Integer limitOfBooks, Boolean isSortedByYear){
        if (page != null && limitOfBooks != null && isSortedByYear !=null) {
            System.out.println("variant #2");
            return booksRepository.findAll(PageRequest.of(page, limitOfBooks, Sort.by("yearOfRealise"))).getContent();
        }else
        if (isSortedByYear !=null) {
            System.out.println("variant #3");
            return booksRepository.findAll(Sort.by("yearOfRealise"));
        }else
        if (page != null && limitOfBooks != null){
            System.out.println("variant #4");
            return booksRepository.findAll(PageRequest.of(page,limitOfBooks)).getContent();
        } else {
            System.out.println("variant #1");
            return booksRepository.findAll();
        }
//        return isSortedByYear ? booksRepository.findAll(Sort.by("yearOfRealise")) : booksRepository.findAll();

    }

    public Book show(int id){
        return booksRepository.findById(id).orElse(null);
    }

    public Optional<Book>show(String title){
        return booksRepository.findBookByTitle(title);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        if (!peopleRepository.findPersonByBookId(id).isPresent())
            updatedBook.setPerson(null);

        updatedBook.setBookId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void makeBookFree(int id){
        show(id).setPerson(null);
    }

    @Transactional
    public void assignPerson(int bookId, int personId){
        Session session = entityManager.unwrap(Session.class);
        Person person = session.load(Person.class, personId);
        show(bookId).setPerson(person);
    }

}