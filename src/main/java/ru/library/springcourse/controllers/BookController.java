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
import ru.library.springcourse.services.BooksService;
import ru.library.springcourse.services.PeopleService;
import ru.library.springcourse.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/library")
public class BookController {

    private final PersonValidator personValidator;
//
//    private final BookValidator bookValidator;

    private final PeopleService peopleService;

    private final BooksService booksService;

//    @Autowired
//    public BookController(PersonValidator personValidator, BookValidator bookValidator, PeopleService peopleService) {
//        this.personValidator = personValidator;
//        this.bookValidator = bookValidator;
//        this.peopleService = peopleService;
//    }

    @Autowired
    public BookController(PersonValidator personValidator, PeopleService peopleService, BooksService booksService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping("/people")
    public String people(Model model) {
//        model.addAttribute("people", personDAO.allReaders());
        model.addAttribute("people", peopleService.allPeople());
//        System.out.println("=======================================");
//        System.out.println(booksService.findAllBooksByPerson(peopleService.allPeople().get(0)));
//        System.out.println("=======================================");
        return "/people/readers";
    }

    @GetMapping("/books")
    public String books(Model model) {
//        model.addAttribute("books", bookDAO.allBooks());


        return "/books/books";
    }

    @GetMapping("/newBook")
    public String newBook(@ModelAttribute Book book) {
        return "books/newBook";
    }

    @PostMapping("/books")  // new parameter
    public String createBook(@ModelAttribute("book") @Valid Book book
            , BindingResult bindingResult) {

//        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/newBook";

//        bookDAO.save(book);


        return "redirect:/library/books";
    }

    @GetMapping("/books/{id}")
    public String showBook(@PathVariable("id") int id, Model model, Model optionalModel,
                           Model modelAddPerson, @ModelAttribute("person") Person person) {

//        model.addAttribute("book", bookDAO.show(id));
//        optionalModel.addAttribute("optionalPerson", bookDAO.bookIsUsedByPerson(id));
//        modelAddPerson.addAttribute("people", personDAO.allReaders());

        return "books/showBook";
    }

    @GetMapping("/books/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
//        model.addAttribute("book", bookDAO.show(id));
        return "books/editBook";
    }

    // чтобы Spring смог читать значение скрытого поля (_method) необходимо использовать фильтр
    @PatchMapping("/books/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") int id) {
//        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/editBook";

//        bookDAO.update(id, book);
        return "redirect:/library/books";
    }

    //изменить на "PatchMapping"
    @GetMapping("/books/{id}/makeFree")
    public String makeBookFree(@PathVariable("id") int id) {
//        bookDAO.makeBookFree(id);
        return "redirect: /library/books/{id}";
    }

    //изменить на "PatchMapping"
    @GetMapping("/books/{id}/assignPerson")
    public String assignPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
//        bookDAO.assignBook(id, person.getPersonId());
        return "redirect: /library/books/{id}";
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable("id") int id) {
//        bookDAO.delete(id);
        return "redirect:/library/books";
    }

    @GetMapping("/newPerson")
    public String newPerson(@ModelAttribute Person person) {
        return "people/newPerson";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person
            , BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/newPerson";

        peopleService.save(person);
        return "redirect:/library/people";
    }

    @GetMapping("/people/{id}")
    public String show(@PathVariable("id") int id, Model model, Model modelBook) {
        model.addAttribute("person", peopleService.show(id));
        modelBook.addAttribute("books", booksService.findAllBooksByPerson(peopleService.show(id)));

        return "people/showPerson";
    }

    @GetMapping("/people/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.show(id));
        return "people/editPerson";
    }

    @PatchMapping("/people/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                               @PathVariable("id") int id) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/editPerson";

        peopleService.update(id, person);
        return "redirect:/library/people";
    }

    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/library/people";
    }

}
