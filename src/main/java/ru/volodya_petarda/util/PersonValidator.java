package ru.volodya_petarda.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.volodya_petarda.dao.PersonDAO;
import ru.volodya_petarda.model.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Person personFromRequest = personDAO.findPerson(person.getName(), person.getSurname(), person.getPatronymic());
        if(personFromRequest != null && personFromRequest.getId() != person.getId()){
            errors.reject("504", "name+surname+patronymic should be unique");
        }
    }
}
