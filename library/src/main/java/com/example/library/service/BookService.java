package com.example.library.service;

import com.example.library.exception.SecurityException;
import com.example.library.exception.UserDoNotBorrowedTheBookException;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.model.UserBook;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserBookRepository;
import com.example.library.repository.UserRepository;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;


public class BookService {
    private Book selectedBook = null;

    private BookService() {
    }

    private final static BookService instance = new BookService();

    public static BookService getInstance() {
        return instance;
    }

    public Book addBook(Book book) throws SQLException, SecurityException {
        if (AuthService.getInstance().isAuthenticated) {
            book.setAdmin_username(AuthService.getInstance().adminInfo.getUsername());
            return BookRepository.getInstance().save(book);
        }
        else throw new SecurityException("Error: access denied!");
    }

    public void updateBook(Book book) throws SQLException, SecurityException {
        if (AuthService.getInstance().isAuthenticated) {
            BookRepository.getInstance().save(book);
        }
        else throw new SecurityException("Error: access denied!");
    }

    public void borrow(Book book, User user, boolean moreThanSevenDay) throws SQLException, SecurityException {
        if (AuthService.getInstance().isAuthenticated) {
            UserBook relation = new UserBook(book.getId(), user.getNational_code());
            UserBookRepository.getInstance().save(relation);
        }
        else throw new SecurityException("Error: access denied!");
    }

    public void unBorrow(Book book) throws SQLException, SecurityException, UserDoNotBorrowedTheBookException {
        if (AuthService.getInstance().isAuthenticated) {
            UserBookRepository.getInstance().delete(
                    UserBookRepository.getInstance().findByBookId(book.getId())
                            .orElseThrow(() -> new UserDoNotBorrowedTheBookException(
                                    "Error: user don't borrowed this book!"
                            ))
            );
        }
        else throw new SecurityException("Error: access denied!");
    }

    public HashSet<Book> getAllBooks() throws SQLException, SecurityException {
        if (AuthService.getInstance().isAuthenticated) {
            HashSet<Book> books = new HashSet<>(BookRepository.getInstance().findAll());
            books.forEach(book->{
                try {
                    book.setUser(
                            UserBookRepository.getInstance().findByBookId(book.getId())
                                    .orElse(new UserBook()).getNational_code()
                    );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return books;
        }
        else throw new SecurityException("Error: access denied!");
    }

    public HashSet<Book> searchBook(String name) throws SQLException, SecurityException {
        if (AuthService.getInstance().isAuthenticated) {
            HashSet<Book> books = new HashSet<>(BookRepository.getInstance().searchByName(name));
            books.forEach(book->{
                try {
                    book.setUser(
                            UserBookRepository.getInstance().findByBookId(book.getId())
                                    .orElse(new UserBook()).getNational_code()
                    );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return books;        }
        else throw new SecurityException("Error: access denied!");
    }

    public HashSet<Book> getAllBooksByAdmin() throws SQLException, SecurityException {
        if (AuthService.getInstance().isAuthenticated) {
            HashSet<Book> books = new HashSet<>(BookRepository.getInstance()
                    .findByAdmin(AuthService.getInstance().adminInfo));
            books.forEach(book->{
                try {
                    book.setUser(
                            UserBookRepository.getInstance().findByBookId(book.getId())
                                    .orElse(new UserBook()).getNational_code()
                    );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return books;
        }
        else throw new SecurityException("Error: access denied!");
    }

    public HashSet<Book> getAllBooksByUser() throws SQLException, SecurityException {
        if (AuthService.getInstance().isAuthenticated) {
            HashSet<UserBook> relations = new HashSet<>(
                    UserBookRepository.getInstance().findAllByNationalCode(
                            UserService.getInstance().getSelected().getNational_code()
                    )
            );

            return relations.stream().map(rel -> {
                try {
                    return BookRepository.getInstance().findById(rel.getBook_id()).orElse(null);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }).filter(Objects::nonNull).map(book -> book.setUser(
                            UserService.getInstance().getSelected().getNational_code()
            )).collect(Collectors.toCollection(HashSet::new));
        }
        else throw new SecurityException("Error: access denied!");
    }

    public void select(Book book){
        this.selectedBook = book;
    }

    public Book getSelected(){
        return selectedBook;
    }

}
