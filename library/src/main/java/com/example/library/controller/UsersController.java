package com.example.library.controller;

import com.example.library.exception.SecurityException;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.BookService;
import com.example.library.service.ScreenService;
import com.example.library.service.UserService;
import com.example.library.view.BookCard;
import com.example.library.view.UserCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class UsersController implements Initializable {

    @FXML private TextField searchField;
    @FXML private GridPane cardHolder;
    ObservableList<UserCard> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Set<User> users = new HashSet<>();
        try {
            users = UserService.getInstance().findAll();
            list.addAll(users.stream().map(UserCard::new).toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            ScreenService.getInstance().activate("login");
        }
        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(20.00);
        cardHolder.setHgap(20.00);
        cardHolder.setStyle("-fx-background: #383838; -fx-padding:10px;-fx-border-color:transparent");

        onSearch(users.size());
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

    public void search(){
        try {
            Set<User> users = UserService.getInstance().search(
                    searchField.getText()
            );
            list = FXCollections.observableArrayList();
            list.addAll(users.stream().map(UserCard::new).toList());
            cardHolder.getChildren().clear();
            onSearch(users.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            ScreenService.getInstance().activate("login");
        }
    }
}
