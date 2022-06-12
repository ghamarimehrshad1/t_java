package com.example.library.repository;

import com.example.library.model.Admin;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.IdService;
import com.example.library.service.SqlService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class BookRepository implements Repository<Book, Integer>{

    private BookRepository(){}
    private static final BookRepository instance = new BookRepository();
    public static BookRepository getInstance() {
        return instance;
    }

    @Override
    public Collection<Book> findAll() throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.books");
        HashSet<Book> books = new HashSet<>();
        while (result.next()) books.add(parseBook(result));
        return new HashSet<>(books);
    }

    @Override
    public Book save(Book book) throws SQLException {
        PreparedStatement preparedStatement;
        if (book.getId() != null) {
            preparedStatement = SqlService.getInstance()
                    .prepareStatement("UPDATE library.books" +
                            " SET" +
                            " name = ?," +
                            " author= ?," +
                            " publication_name= ?," +
                            " circulation= ?," +
                            " publishing_turn= ?," +
                            " price= ?" +
                            " WHERE" +
                            " id = ?"
                    );
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPublication_name());
            preparedStatement.setInt(4, book.getCirculation());
            preparedStatement.setInt(5, book.getPublishing_turn());
            preparedStatement.setInt(6, book.getPrice());
            preparedStatement.setInt(7, book.getId());
        }
        else{
            preparedStatement = SqlService.getInstance()
                    .prepareStatement("insert into library.books values (?, ?, ?, ?, ?, ?, ?, ?)");

            int id = IdService.getInstance().generateId();
            book.setId(id);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPublication_name());
            preparedStatement.setInt(5, book.getCirculation());
            preparedStatement.setInt(6, book.getPublishing_turn());
            preparedStatement.setInt(7, book.getPrice());
            preparedStatement.setString(8, book.getAdmin_username());
        }
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return book;
    }

    @Override
    public void delete(Book book) throws SQLException {
        PreparedStatement preparedStatement = SqlService.getInstance()
                .prepareStatement("DELETE FROM library.books" +
                        " where" +
                        " id = '" + book.getId() + "'");
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public Optional<Book> findById(Integer id) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.books" +
                        " where" +
                        " id = '" + id + "'");

        return Optional.ofNullable(
                result.next()? parseBook(result):null
        );
    }

    public Collection<Book> findByAdmin(Admin admin) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.books" +
                        " where" +
                        " admin_username = '" + admin.getUsername() + "'");

        HashSet<Book> books = new HashSet<>();
        while (result.next()) books.add(parseBook(result));
        return new HashSet<>(books);
    }
    public Collection<Book> searchByName(String name) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.books" +
                        " where" +
                        " name like '%" + name + "%'");
        HashSet<Book> books = new HashSet<>();
        while (result.next()) books.add(parseBook(result));
        return new HashSet<>(books);
    }

    private Book parseBook(ResultSet result) throws SQLException {
        Book book = new Book();
        book.setId(result.getInt("id"));
        book.setName(result.getString("name"));
        book.setAuthor(result.getString("author"));
        book.setCirculation(result.getInt("circulation"));
        book.setPrice(result.getInt("price"));
        book.setPublication_name(result.getString("publication_name"));
        book.setPublishing_turn(result.getInt("publishing_turn"));
        book.setAdmin_username(result.getString("admin_username"));
        return book;
    }
}
