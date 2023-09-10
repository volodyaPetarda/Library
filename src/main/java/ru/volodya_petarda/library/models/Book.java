package ru.volodya_petarda.library.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "is_free")
    private boolean isFree = true;
    @Column(name = "name")
    @Size(min=5, max=300, message = "name should be more than 5 and less than 300 symbols")
    private String name;
    @Column(name = "author")
    @Size(min=5, max=300, message = "author name should be more than 5 and less than 300 symbols")
    private String author;
    @Column(name = "year")
    private int year;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date takenAt;
    @ManyToOne()
    @JoinColumn(name = "reader_id")
    private Person reader;
    @Transient
    private boolean isExpired;

    public Book(){

    }

    public Book(long id, boolean isFree, String name, String author, int year) {
        this.id = id;
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

    public boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(boolean free) {
        isFree = free;
    }

    public void setReader(Person reader){
        releaseReader();
        this.reader = reader;
        if(!reader.getBooks().contains(this))
            reader.addBook(this);
    }
    public Person getReader(){
        return reader;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean getIsExpired(){
        return isExpired;
    }
    public void setIsExpired(boolean isExpired){
        this.isExpired = isExpired;
    }
    public void releaseReader(){
        Person tempReader = reader;
        reader = null;
        if(tempReader != null)
            tempReader.deleteBook(this);
        isFree = true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && isFree == book.isFree && year == book.year && Objects.equals(name, book.name) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isFree, name, author, year);
    }
}
