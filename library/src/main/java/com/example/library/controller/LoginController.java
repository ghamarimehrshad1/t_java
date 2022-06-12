package com.example.library.controller;

import com.example.library.exception.SecurityException;
import com.example.library.model.Book;
import com.example.library.service.AuthService;
import com.example.library.service.BookService;
import com.example.library.service.ScreenService;
import com.example.library.service.SqlService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class LoginController {
    @FXML public TextField usernameField;
    @FXML public PasswordField passwordField;
    @FXML public Text error;

    @FXML public void login() {
        try {
            error.setText(null);
            AuthService.getInstance().login(usernameField.getText(),passwordField.getText());
//            BookService.getInstance().addBook(new Book(
//                    "name 1", "name 1", "name 1",
//                    1,1,1
//            ));
//            BookService.getInstance().addBook(new Book(
//                    "name 2", "name 2", "name 2",
//                    2,2,2
//            ));
//            BookService.getInstance().addBook(new Book(
//                    "name 3", "name 3", "name 3",
//                    3,3,3
//            ));
//            BookService.getInstance().addBook(new Book(
//                    "name 4", "name 4", "name 4",
//                    4,4,4
//            ));
//            BookService.getInstance().addBook(new Book(
//                    "name 5", "name 5", "name 5",
//                    5,5,5
//            ));
//            BookService.getInstance().addBook(new Book(
//                    "name 6", "name 6", "name 6",
//                    6,6,6
//            ));
//            BookService.getInstance().addBook(new Book(
//                    "name 7", "name 7", "name 7",
//                    7,7,7
//            ));
            ScreenService.getInstance().activate("admin-panel");
        } catch (SecurityException | SQLException e){
            error.setText(e.getMessage());
        } finally {
            SqlService.getInstance().close();
        }
    }
    @FXML public void goToRegister() {
        ScreenService.getInstance().activate("register");
    }
}
