package ru.volodya_petarda.services;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.volodya_petarda.models.Book;
import ru.volodya_petarda.models.Person;
import ru.volodya_petarda.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleService peopleService;

    public BooksService(BooksRepository booksRepository, PeopleService peopleService){
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
    }
    public List<Book> findAll(){
        return booksRepository.findAll();
    }
    public List<Book> findAllWithSort(){
        return booksRepository.findAll(Sort.by("year"));
    }
    public List<Book> findAllWithPagination(int page, int booksPerPage){
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }
    public List<Book> findAllWithPaginationAndSort(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
    }
    public Optional<Book> find(long id){
        return booksRepository.findById(id);
    }
    public Optional<Book> find(String name, String author){
        return booksRepository.findByNameAndAuthor(name, author);
    }
    public void delete(long id){
        booksRepository.deleteById(id);
    }
    public void save(Book book){
        booksRepository.save(book);
    }
    public void setReader(long id, long readerId){
        Book book = booksRepository.findById(id).orElseThrow(RuntimeException::new);
        Person reader = peopleService.find(readerId).orElseThrow(RuntimeException::new);
        book.setReader(reader);
        book.setIsFree(false);
        book.setTakenAt(new Date());
    }
    public void releaseReader(long id){
        Book book = booksRepository.findById(id).orElseThrow(RuntimeException::new);
        book.releaseReader();
    }
    public List<Book> findByStartingName(String name){
        return booksRepository.findByNameStartingWith(name);
    }
    public void calculateIsExpired(Book book, long days){
        if(!book.getIsFree()){
            long diffInMillis = new Date().getTime() - book.getTakenAt().getTime();
            long diffInDays = diffInMillis / 1000 / 60 / 60 / 24;
            book.setIsExpired(diffInDays > days);
        }
    }
    public void calculateIsExpired(List<Book> books, long days){
        for(Book book : books)
            calculateIsExpired(book, days);
    }
}
