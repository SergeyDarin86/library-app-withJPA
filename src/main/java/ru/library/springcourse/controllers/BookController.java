package ru.library.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.springcourse.dao.BookDAO;
import ru.library.springcourse.dao.PersonDAO;
import ru.library.springcourse.models.Book;
import ru.library.springcourse.models.Person;
import ru.library.springcourse.util.BookValidator;
import ru.library.springcourse.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/library")
public class BookController {

    private final PersonDAO personDAO;

    private final BookDAO bookDAO;

    private final PersonValidator personValidator;

    private final BookValidator bookValidator;

    @Autowired
    public BookController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator, BookValidator bookValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
        this.bookValidator = bookValidator;
    }

    @GetMapping("/people")
    public String people(Model model){
        model.addAttribute("people", personDAO.allReaders());

//        List<Person>personList = personDAO.allReaders();
//        personList.stream().forEach(person -> {System.out.println(person.getPersonId() + " " + person.getFullName());});
        return "/people/readers";
    }

    @GetMapping("/books")
    public String books(Model model){
        model.addAttribute("books", bookDAO.allBooks());
        return "/books/books";
    }

    @GetMapping("/newBook")
    public String newBook(@ModelAttribute Book book) {
        return "books/newBook";
    }

    @PostMapping("/books")  // new parameter
    public String createBook(@ModelAttribute("book") @Valid Book book
            , BindingResult bindingResult) {

        bookValidator.validate(book,bindingResult);

        if (bindingResult.hasErrors())
            return "books/newBook";

        bookDAO.save(book);
        return "redirect:/library/books";
    }

    @GetMapping("/books/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        // получим одной книги по её id из DAO и передадим на отображение в представление
        model.addAttribute("book", bookDAO.show(id));

        System.out.println(id + " ID from books");
        return "books/showBook";
    }

    @GetMapping("/newPerson")
    public String newPerson(@ModelAttribute Person person) {
        return "people/newPerson";
    }

    //TODO: посмотреть почему проблема с валидатором!!!
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person
            , BindingResult bindingResult) {

        personValidator.validate(person,bindingResult);

        if (bindingResult.hasErrors())
            return "people/newPerson";

        personDAO.save(person);
        return "redirect:/library/people";
    }

    @GetMapping("/people/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        // получим одного человека по его id из DAO и передадим на отображение в представление
        model.addAttribute("person", personDAO.show(id));

        System.out.println(id + " ID from readers");
        return "people/showPerson";
    }




}
