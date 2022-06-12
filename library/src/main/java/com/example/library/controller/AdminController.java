package com.example.library.controller;

import com.example.library.exception.SecurityException;
import com.example.library.model.Book;
import com.example.library.service.AuthService;
import com.example.library.service.BookService;
import com.example.library.service.ScreenService;
import com.example.library.view.BookCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AdminController implements Initializable {


    public Text nameTxt;
    public Text usernameTxt;
    @FXML private GridPane cardHolder;
    ObservableList<BookCard> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameTxt.setText(
                AuthService.getInstance().adminInfo.getName()
        );
        usernameTxt.setText(
                "@"+AuthService.getInstance().adminInfo.getUsername()
        );
        Set<Book> books = new HashSet<>();
        try {
            books = BookService.getInstance().getAllBooksByAdmin();
            list.addAll(books.stream().map(BookCard::new).toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            ScreenService.getInstance().activate("login");
        }
        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(20.00);
        cardHolder.setHgap(20.00);
        cardHolder.setStyle("-fx-background: #383838; -fx-padding:10px;-fx-border-color:transparent");

        onSearch(books.size());
    }

    @FXML
    public void onSearch(int size) {
        if (size==0) return;
        int count = size;
        for (int i = 0; i <= size/2; i++) {
            for (int j = 0; j < 2; j++) {
                cardHolder.add(list.get(count-1), j, i);
                count--;
                if (count==0) return;
            }
        }
    }
}
