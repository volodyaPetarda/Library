package ru.volodya_petarda.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.volodya_petarda.library.models.Book;
import ru.volodya_petarda.library.services.BooksService;

import java.util.Optional;

@Component
public class BookValidator implements Validator {
    private final BooksService booksService;

    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book)target;
        Optional<Book> bookFromRequest = booksService.find(book.getName(), book.getAuthor());
        if(bookFromRequest.isPresent() && bookFromRequest.get().getId() != book.getId()){
            errors.reject("504", "name+author should be unique");
        }
    }
}
