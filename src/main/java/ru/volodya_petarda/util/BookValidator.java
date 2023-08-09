package ru.volodya_petarda.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.volodya_petarda.dao.BookDAO;
import ru.volodya_petarda.model.Book;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book)target;
        Book bookFromRequest = bookDAO.findBook(book.getName(), book.getAuthor());
        if(bookFromRequest != null && bookFromRequest.getId() != book.getId()){
            errors.reject("504", "name+author should be unique");
        }
    }
}
