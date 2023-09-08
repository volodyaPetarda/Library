package ru.volodya_petarda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.volodya_petarda.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    public Optional<Book> findByNameAndAuthor(String name, String author);
    public List<Book> findByNameStartingWith(String name);
}
