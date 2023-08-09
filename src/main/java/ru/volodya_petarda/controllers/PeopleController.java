package ru.volodya_petarda.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.volodya_petarda.dao.BookDAO;
import ru.volodya_petarda.dao.PersonDAO;
import ru.volodya_petarda.model.Book;
import ru.volodya_petarda.model.Person;
import ru.volodya_petarda.util.PersonValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;

    public PeopleController(PersonDAO personDAO, PersonValidator personValidator, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personDAO.getListOfPeople());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "/people/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        Person person = personDAO.findPerson(id);
        model.addAttribute("person", person);
        List<Book> listOfBooks = bookDAO.findListOfBooks(person.getId());
        model.addAttribute("books", listOfBooks);
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model){
        Person person = personDAO.findPerson(id);
        model.addAttribute("person", person);
        return "people/edit";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/new";

        personDAO.addPerson(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id){
        personDAO.deletePerson(id);
        return "redirect:/people";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/edit";

        personDAO.updatePerson(person);
        return "redirect:/people";
    }
}
