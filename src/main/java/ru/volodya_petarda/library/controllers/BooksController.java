package ru.volodya_petarda.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.volodya_petarda.library.models.Book;
import ru.volodya_petarda.library.models.Person;
import ru.volodya_petarda.library.services.BooksService;
import ru.volodya_petarda.library.services.PeopleService;
import ru.volodya_petarda.library.util.BookValidator;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final PeopleService peopleService;
    private final BooksService booksService;
    private final BookValidator bookValidator;

    public BooksController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort", required = false) Boolean sort){
        List<Book> listOfBooks;
        if(page != null && booksPerPage != null && sort != null && sort)
            listOfBooks = booksService.findAllWithPaginationAndSort(page, booksPerPage);
        else if(page != null && booksPerPage != null && (!sort || sort == null))
            listOfBooks = booksService.findAllWithPagination(page, booksPerPage);
        else if(sort != null && sort)
            listOfBooks = booksService.findAllWithSort();
        else
            listOfBooks = booksService.findAll();
        model.addAttribute("books", listOfBooks);
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        Book book = booksService.find(id).orElseThrow(RuntimeException::new);
        model.addAttribute("book", book);
        if(book.getIsFree()){
            model.addAttribute("people", peopleService.findAll());
            model.addAttribute("newReader", new Person());
        }
        else{
            model.addAttribute("reader", book.getReader());
        }

        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model){
        Book book = booksService.find(id).orElseThrow(RuntimeException::new);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @GetMapping("/search")
    public String search(){
        return "books/search";
    }

    @GetMapping("/show_found")
    public String showFound(@RequestParam("name") String name, Model model){
        model.addAttribute("books", booksService.findByStartingName(name));
        return "books/show_found";
    }

    @PostMapping("/new")
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/edit";
        booksService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseReader(@PathVariable("id") long id){
        booksService.releaseReader(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/set_reader")
    public String setReader(@PathVariable("id") long id, @ModelAttribute("newReader") Person person){

        booksService.setReader(id, person.getId());
        return "redirect:/books";
    }
}
