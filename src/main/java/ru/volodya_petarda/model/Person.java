package ru.volodya_petarda.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class Person {
    private long id;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]{1,99}", message = "incorrect name")
    private String name;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]{1,99}", message = "incorrect surname")
    private String surname;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]{1,99}", message = "incorrect patronymic")
    private String patronymic;
    @Min(value = 1901, message = "year of birth should be greater than 1900")
    private int yearOfBirth;

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
}
