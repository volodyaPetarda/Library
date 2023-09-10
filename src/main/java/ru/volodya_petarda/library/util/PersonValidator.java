package ru.volodya_petarda.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.volodya_petarda.library.models.Person;
import ru.volodya_petarda.library.services.PeopleService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> personFromRequest = peopleService.find(person.getName(), person.getSurname(), person.getPatronymic());
        if(personFromRequest.isPresent() && personFromRequest.get().getId() != person.getId()){
            errors.reject("504", "name+surname+patronymic should be unique");
        }
    }
}
