package com.example.library.service;

import com.example.library.exception.NationalCodeAlreadyTakenException;
import com.example.library.exception.SecurityException;
import com.example.library.exception.UserBorrowedBooksException;
import com.example.library.exception.UserNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.model.UserBook;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserBookRepository;
import com.example.library.repository.UserRepository;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserService {

    private UserService(){}
    private static  final UserService instance = new UserService();
    public static UserService getInstance() {
        return instance;
    }

    private User selectedUser = null;
    public User create(User user) throws SQLException, NationalCodeAlreadyTakenException {
        if (UserRepository.getInstance().findByNationalCode(user.getNational_code()).isEmpty()){
            UserRepository.getInstance().save(user);
            return user;
        }
        else throw new NationalCodeAlreadyTakenException("Error: national number is already taken!");
    }

    public void update(User user) throws SQLException, UserNotFoundException {
        if (UserRepository.getInstance().findByNationalCode(user.getNational_code()).isPresent()){
            UserRepository.getInstance().save(user);
        }
        else throw new UserNotFoundException("Error: user not found!");
    }

    public void delete(User user) throws SQLException, UserBorrowedBooksException {
        if (UserBookRepository.getInstance().findAllByNationalCode(user.getNational_code()).isEmpty()){
            UserRepository.getInstance().delete(user);
        }
        else throw new UserBorrowedBooksException("Error: user borrowed some books and can't be deleted!");
    }

    public User getUserByNationalCode(String nationalCode) throws SQLException, UserNotFoundException {
        return UserRepository.getInstance().findByNationalCode(nationalCode)
                .orElseThrow(()->new UserNotFoundException("Error: user not found!"));
    }

    public Set<Book> getUserBorrowedBooks(User user) throws SQLException {
        return UserBookRepository.getInstance().findAllByNationalCode(user.getNational_code())
                .stream().map(relation-> {
                    try {
                        return BookRepository.getInstance().findById(relation.getBook_id()).orElse(null);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toSet());
    }
    public Set<User> findAll() throws SecurityException, SQLException {
        if (AuthService.getInstance().isAuthenticated) {
            return new HashSet<>(UserRepository.getInstance().findAll());
        }
        else throw new SecurityException("Error: access denied!");
    }

    public Set<User> search(String nationalNum) throws SecurityException, SQLException {
        if (AuthService.getInstance().isAuthenticated) {
            return new HashSet<>(UserRepository.getInstance().searchByNationalNum(nationalNum));
        }
        else throw new SecurityException("Error: access denied!");
    }
    public void select(User user){
        selectedUser = user;
    }
    public void unSelect(){
        selectedUser = null;
    }
    public User getSelected(){
        return selectedUser;
    }
}
