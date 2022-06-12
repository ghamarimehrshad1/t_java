package com.example.library.view;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.BookService;
import com.example.library.service.ScreenService;
import com.example.library.service.UserService;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserCard extends VBox {

    protected final Text nameKey, fatherKey, national_numberKey, addressKey;
    protected final Text nameValue, fatherValue, national_numberValue, addressValue;

    public UserCard(User user) {
        setPrefHeight(180.0);setPrefWidth(250.0);

        setStyle("-fx-background-color: #fff; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        nameValue = createValue(user.getFull_name());
        fatherValue = createValue(user.getFather_name());
        national_numberValue = createValue(user.getNational_code());
        addressValue = createValue(user.getAddress());

        nameKey = createKey("Full Name:");
        fatherKey = createKey("Father Name:");
        national_numberKey = createKey("National Num:");
        addressKey = createKey("Address:");

        createRow(nameKey,nameValue);
        createRow(fatherKey, fatherValue);
        createRow(national_numberKey, national_numberValue);
        createRow(addressKey, addressValue);

        setOnMouseClicked(e -> {
            UserService.getInstance().select(user);
            ScreenService.getInstance().changeView("user");
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
        key.setLayoutX(25);
        return key;
    }
    private Text createValue(String txt){
        Text value = new Text(txt);
        value.setLayoutY(27);
        value.setLayoutX(25);
        return value;
    }
}