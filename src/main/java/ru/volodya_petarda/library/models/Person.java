package ru.volodya_petarda.library.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]{1,99}", message = "incorrect name")
    private String name;
    @Column(name = "surname")
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]{1,99}", message = "incorrect surname")
    private String surname;
    @Column(name = "patronymic")
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]{1,99}", message = "incorrect patronymic")
    private String patronymic;
    @Column(name = "year_of_birth")
    @Min(value = 1901, message = "year of birth should be greater than 1900")
    private int yearOfBirth;

    @OneToMany(mappedBy = "reader")
    private List<Book> books;

    public Person(){

    }
    public Person(long id, String name, String surname, String patronymic, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.yearOfBirth = yearOfBirth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks(){
        return books;
    }
    public void setBooks(List<Book> books){
        this.books = books;
    }
    public void addBook(Book book){
        if(!books.contains(book))
            books.add(book);
        if(!book.getReader().equals(this))
            book.setReader(this);
    }
    public void deleteBook(Book book) {
        books.remove(book);
        if(book.getReader() == this)
            book.releaseReader();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && yearOfBirth == person.yearOfBirth && Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(patronymic, person.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, patronymic, yearOfBirth);
    }

}
