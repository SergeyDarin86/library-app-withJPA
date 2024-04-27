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
import java.util.Date;
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

    //TODO: переделать стремную логику в этом методе
    public List<Book> findAllBooksByPerson(Person person) {
        countDaysTheBookIsTakenByPersonNew(person);
        return booksRepository.findAllByPerson(person);
    }

    public void countDaysTheBookIsTakenByPersonNew(Person person) {
        booksRepository.findAllByPerson(person).
                forEach(book -> book.setIsTakenMoreThan10Days(differenceBetweenTwoDates(countOfDaysForSingleDate(new Date()), countOfDaysForSingleDate(book.getTakenAt())) > 10));
    }

    //получаем количество дней, прошедших от эпохальной даты
    public Integer countOfDaysForSingleDate(Date date) {
        return (int) date.toInstant().getEpochSecond() / 60 / 60 / 24;
    }

    // высчитываем разницу в днях между текущей датой и датой, когда взяли книгу
    public Integer differenceBetweenTwoDates(int daysForToday, int daysForTakenBook) {
        return daysForToday - daysForTakenBook;
    }

    public List<Book> findAll(Integer page, Integer limitOfBooks, Boolean isSortedByYear) {

        if (page != null && limitOfBooks != null && isSortedByYear != null) {
            return booksRepository.findAll(PageRequest.of(page, limitOfBooks, Sort.by("yearOfRealise"))).getContent();
        } else if (isSortedByYear != null && isSortedByYear) {
            return booksRepository.findAll(Sort.by("yearOfRealise"));
        } else if (page != null && limitOfBooks != null) {
            return booksRepository.findAll(PageRequest.of(page, limitOfBooks)).getContent();
        } else {
            return booksRepository.findAll();
        }

    }

    public Book show(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public Optional<Book> show(String title) {
        return booksRepository.findBookByTitle(title);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        if (!peopleRepository.findPersonByBookId(id).isPresent())
            updatedBook.setPerson(null);

        updatedBook.setBookId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void makeBookFree(int id) {
        show(id).setTakenAt(null);
        show(id).setPerson(null);
    }

    @Transactional
    public void assignPerson(int bookId, int personId) {
        Session session = entityManager.unwrap(Session.class);
        Person person = session.load(Person.class, personId);
        show(bookId).setTakenAt(new Date());
        show(bookId).setPerson(person);
    }

    // Недостаток данного метода поиска в том, что должен вернуться только один результат,
    // но по факту могут быть две книги, у которых название начинается с одних и тех же символов (слов)
    // например: "Основы инвестиций" и "Основы программирование"
    public Optional<Book> getBookByTitleStartingWith(String title){
//        return booksRepository.findBookByTitleStartingWith(title);
        return null;
    }

    // Метод, который будет возвращать List<Book>
    public List<Book> getBookListByTitleStartingWith(String title){
        return booksRepository.findBookByTitleStartingWith(title);
    }
}
