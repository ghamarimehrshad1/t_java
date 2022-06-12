package com.example.library.view;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.example.library.service.ScreenService;
import javafx.scene.text.*;
import javafx.scene.layout.*;

public class BookCard extends VBox {

    protected final Text nameKey,authorKey,publication_nameKey,circulationKey,publishing_turnKey,priceKey,admin_usernameKey;
    protected final Text nameValue,authorValue,publication_nameValue,circulationValue,publishing_turnValue,priceValue,admin_usernameValue;

    public BookCard(Book book) {
        setPrefHeight(315.0);setPrefWidth(230.0);

        if (book.getUser()==null)
            setStyle("-fx-background-color: #C0D8C0; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        else
            setStyle("-fx-background-color: #FFBBBB; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        nameValue = createValue(book.getName());
        authorValue = createValue(book.getAuthor());
        publication_nameValue = createValue(book.getPublication_name());
        circulationValue = createValue(book.getCirculation().toString());
        publishing_turnValue = createValue(book.getPublishing_turn().toString());
        priceValue = createValue(book.getPrice().toString());
        admin_usernameValue = createValue(book.getAdmin_username());

        nameKey = createKey("Name:");
        authorKey = createKey("Author:");
        publication_nameKey = createKey("Publication:");
        circulationKey = createKey("Circulation:");
        publishing_turnKey = createKey("Publishing");
        priceKey = createKey("Price:");
        admin_usernameKey = createKey("Admin:");

        createRow(nameKey,nameValue);
        createRow(authorKey,authorValue);
        createRow(publication_nameKey,publication_nameValue);
        createRow(circulationKey,circulationValue);
        createRow(publishing_turnKey,publishing_turnValue);
        createRow(priceKey,priceValue);
        createRow(admin_usernameKey,admin_usernameValue);

        setOnMouseClicked(e -> {
            BookService.getInstance().select(book);
            ScreenService.getInstance().changeView("book");
        });
    }

    private void createRow(Text key, Text value){
        HBox hBox = new HBox();
        hBox.setPrefHeight(45.0); hBox.setPrefWidth(250.0);

        Pane pane1 = new Pane();
        pane1.setPrefHeight(45.0); pane1.setPrefWidth(100.0);
        pane1.getChildren().add(key);

        Pane pane2 = new Pane();
        pane2.setPrefHeight(45.0); pane2.setPrefWidth(150);
        pane2.getChildren().add(value);

        hBox.getChildren().addAll(pane1,pane2);
        getChildren().add(hBox);
    }

    private Text createKey(String txt){
        Text key = new Text(txt);
        key.setLayoutY(27);
        key.setLayoutX(15);
        return key;
    }
    private Text createValue(String txt){
        Text value = new Text(txt);
        value.setLayoutY(27);
        value.setLayoutX(15);
        return value;
    }
}