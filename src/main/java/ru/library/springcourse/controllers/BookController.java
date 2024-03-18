package ru.library.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.library.springcourse.dao.PersonDAO;

@Controller
@RequestMapping("/library")
public class BookController {

    private final PersonDAO personDAO;

    @Autowired
    public BookController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/people")
    public String people(Model model){
        model.addAttribute("people", personDAO.allReaders());
        return "/people/readers";
    }

}
