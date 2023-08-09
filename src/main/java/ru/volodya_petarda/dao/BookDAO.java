package ru.volodya_petarda.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.volodya_petarda.model.Book;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void addBook(Book book){
        jdbcTemplate.update("INSERT INTO book(reader_id, is_free, name, author, year) VALUES(?, ?, ?, ?, ?)",
                book.getReaderId(), book.getIsFree(), book.getName(), book.getAuthor(), book.getYear());
    }
    public Book findBook(long id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
    public Book findBook(String name, String author){
        return jdbcTemplate.query("SELECT * FROM book WHERE name = ? AND author = ?", new Object[]{name, author}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
    public List<Book> getListOfBooks(){
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> findListOfBooks(long readerId){
        return jdbcTemplate.query("SELECT * FROM book WHERE reader_id = ?", new Object[]{readerId}, new BeanPropertyRowMapper<>(Book.class));
    }
    public List<Book> getListOfBooksByReaderId(long readerId){
        return jdbcTemplate.query("SELECT * FROM book WHERE reader_id = ?",
                new Object[]{readerId}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void deleteBook(long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public void updateBook(Book book) {
        jdbcTemplate.update("UPDATE book SET name=?, author=?, year=? WHERE id = ?", book.getName(), book.getAuthor(), book.getYear(), book.getId());
    }
    public void releaseReader(Book book){
        jdbcTemplate.update("UPDATE book SET is_free=true, reader_id=null WHERE id=?", book.getId());
    }

    public void setReader(Book book){
        jdbcTemplate.update("UPDATE book SET is_free=false, reader_id=? WHERE id=?", book.getReaderId(), book.getId());
    }
}
