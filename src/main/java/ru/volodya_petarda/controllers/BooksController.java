package ru.volodya_petarda.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.volodya_petarda.dao.BookDAO;
import ru.volodya_petarda.dao.PersonDAO;
import ru.volodya_petarda.model.Book;
import ru.volodya_petarda.model.Person;
import ru.volodya_petarda.util.BookValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model){
        List<Book> listOfBooks = bookDAO.getListOfBooks();
        model.addAttribute("books", listOfBooks);
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        Book book = bookDAO.findBook(id);
        model.addAttribute("book", book);
        if(book.getIsFree()){
            model.addAttribute("people", personDAO.getListOfPeople());
            model.addAttribute("newReader", new Person());
        }
        else{
            model.addAttribute("reader", personDAO.findPerson(book.getReaderId()));
        }

        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model){
        Book book = bookDAO.findBook(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PostMapping("/new")
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/new";

        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id){
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/edit";
        bookDAO.updateBook(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseReader(@PathVariable("id") long id){
        Book book = bookDAO.findBook(id);
        bookDAO.releaseReader(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/set_reader")
    public String setReader(@PathVariable("id") long id, @ModelAttribute("newReader") Person person){
        Book book = bookDAO.findBook(id);
        book.setReaderId(person.getId());
        bookDAO.setReader(book);
        return "redirect:/books";
    }
}
