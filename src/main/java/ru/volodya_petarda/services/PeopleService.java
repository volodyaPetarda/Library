package ru.volodya_petarda.services;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.volodya_petarda.models.Person;
import ru.volodya_petarda.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BooksService booksService;
    public PeopleService(PeopleRepository peopleRepository, @Lazy BooksService booksService){
        this.peopleRepository = peopleRepository;
        this.booksService = booksService;
    }
    public List<Person> findAll(){
        return peopleRepository.findAll();
    }
    public void save(Person person){
        peopleRepository.save(person);
    }
    public Optional<Person> find(long id){
        return peopleRepository.findById(id);
    }
    public Optional<Person> find(String name, String surname, String patronymic){
        return peopleRepository.findByNameAndSurnameAndPatronymic(name, surname, patronymic);
    }
    public void delete(long id){
        peopleRepository.deleteById(id);
    }

    public Person initBooks(Person p){
        Person person = peopleRepository.findById(p.getId()).orElseThrow(RuntimeException::new);
        booksService.calculateIsExpired(person.getBooks(), 10);
        Hibernate.initialize(person.getBooks());
        return person;
    }
}
