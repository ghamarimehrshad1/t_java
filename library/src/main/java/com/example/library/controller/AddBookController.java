package com.example.library.controller;

import com.example.library.exception.SecurityException;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.example.library.service.ScreenService;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class AddBookController {
    public Text error;
    public TextField priceField;
    public TextField publicationTurnField;
    public TextField circulationField;
    public TextField publicationNameField;
    public TextField authorField;
    public TextField nameField;

    public void addBook(){
        try {
            BookService.getInstance().addBook(new Book(
                    nameField.getText(),
                    authorField.getText(),
                    publicationNameField.getText(),
                    Integer.parseInt(circulationField.getText()),
                    Integer.parseInt(publicationTurnField.getText()),
                    Integer.parseInt(priceField.getText())
            ));
            ScreenService.getInstance().changeView("books");
        } catch (Exception e) {
            clear();
            error.setText(e.getMessage());
        }
    }
    private void clear(){
        nameField.clear();
        authorField.clear();
        publicationNameField.clear();
        circulationField.clear();
        publicationTurnField.clear();
        priceField.clear();
    }
}
