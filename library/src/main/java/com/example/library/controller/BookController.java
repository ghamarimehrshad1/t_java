package com.example.library.controller;

import com.example.library.exception.UserNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.BookService;
import com.example.library.service.ScreenService;
import com.example.library.service.UserService;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookController implements Initializable {

    public TextField nameField;
    public TextField authorField;
    public TextField publicationNameField;
    public TextField circulationField;
    public TextField publicationTurnField;
    public TextField priceField;
    public Text error;
    public Button borrow;
    public TextField nationalNumField;
    public Label nationalNumLbl;
    private Book book;
    public User user = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        book = BookService.getInstance().getSelected();
        nameField.setText(book.getName());
        authorField.setText(book.getAuthor());
        publicationNameField.setText(book.getPublication_name());
        circulationField.setText(book.getCirculation().toString());
        publicationTurnField.setText(book.getPublishing_turn().toString());
        priceField.setText(book.getPrice().toString());

        if (book.getUser() != null){
            try {
                user = UserService.getInstance().getUserByNationalCode(book.getUser());
                nationalNumField.setText(book.getUser());
                nationalNumField.setEditable(false);
            } catch (UserNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        borrow.setText(
                user==null? "Borrow":"Un Borrow"
        );
    }

    public void update(){
        try {
            BookService.getInstance().updateBook(
                    book
                            .setName(nameField.getText())
                            .setAuthor(authorField.getText())
                            .setPublication_name(publicationNameField.getText())
                            .setCirculation(Integer.parseInt(circulationField.getText()))
                            .setPublishing_turn(Integer.parseInt(publicationTurnField.getText()))
                            .setPrice(Integer.parseInt(priceField.getText()))
            );
            ScreenService.getInstance().changeView("books");
        } catch (Exception e) {
            error.setText(e.getMessage());
        }
    }

    public void onBorrowClicked(){
        if (user!=null){
            try {
                BookService.getInstance().unBorrow(book);
                ScreenService.getInstance().changeView("books");
            } catch (Exception e) {
                error.setText(e.getMessage());
            }
        }else {
            try {
                BookService.getInstance()
                        .borrow(
                                book,
                                UserService.getInstance().getUserByNationalCode(nationalNumField.getText()),
                                false
                        );
                ScreenService.getInstance().changeView("books");
            } catch (Exception e) {
                error.setText(e.getMessage());
            }
        }

    }
}
