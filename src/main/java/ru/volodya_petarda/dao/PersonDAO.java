package ru.volodya_petarda.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.volodya_petarda.model.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getListOfPeople(){
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void addPerson(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, surname, patronymic, year_of_birth) VALUES(?, ?, ?, ?)",
                person.getName(), person.getSurname(), person.getPatronymic(), person.getYearOfBirth());
    }

    public Person findPerson(Long id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Person findPerson(String name, String surname, String patronymic){
        return jdbcTemplate.query("SELECT * FROM person where name = ? AND surname = ? AND patronymic = ?",
                new Object[]{name, surname, patronymic}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }
    public void deletePerson(long id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public void updatePerson(Person person) {
        jdbcTemplate.update("UPDATE person SET name=?, surname=?, patronymic=? WHERE id=?",
                person.getName(), person.getSurname(), person.getPatronymic(), person.getId());
    }
}
