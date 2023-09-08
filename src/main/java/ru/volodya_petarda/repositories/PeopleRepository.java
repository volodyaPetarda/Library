package ru.volodya_petarda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.volodya_petarda.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    public Optional<Person> findByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);
}
