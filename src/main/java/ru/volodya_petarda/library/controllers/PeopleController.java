package ru.volodya_petarda.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.volodya_petarda.library.models.Person;
import ru.volodya_petarda.library.services.PeopleService;
import ru.volodya_petarda.library.util.PersonValidator;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    public PeopleController(PersonValidator personValidator, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "/people/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        Person person = peopleService.find(id).orElseThrow(RuntimeException::new);
        person = peopleService.initBooks(person);
        model.addAttribute("person", person);
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model){
        Person person = peopleService.find(id).orElseThrow(RuntimeException::new);
        model.addAttribute("person", person);
        return "people/edit";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id){
        peopleService.delete(id);
        return "redirect:/people";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/edit";

        peopleService.save(person);
        return "redirect:/people";
    }
}
