package ru.volodya_petarda.model;

import javax.validation.constraints.Size;

public class Book {
    private long id;
    private Long readerId;
    private boolean isFree = true;
    @Size(min=5, max=300, message = "name should be more than 5 and less than 300 symbols")
    private String name;
    @Size(min=5, max=300, message = "author name should be more than 5 and less than 300 symbols")
    private String author;
    private int year;

    public Book(){

    }

    public Book(long id, long readerId, boolean isFree, String name, String author, int year) {
        this.id = id;
        this.readerId = readerId;
        this.isFree = isFree;
        this.name = name;
        this.author = author;
        this.year = year;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(boolean free) {
        isFree = free;
    }
}
