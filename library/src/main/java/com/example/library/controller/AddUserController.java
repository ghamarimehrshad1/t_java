package com.example.library.controller;

import com.example.library.model.User;
import com.example.library.service.ScreenService;
import com.example.library.service.UserService;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddUserController {
    public TextField nameField;
    public TextField nationalNumField;
    public TextField fatherNameField;
    public TextField addressField;
    public Text error;

    public void addUser(){
        try {
            UserService.getInstance().create(new User(
                    nameField.getText(),
                    nationalNumField.getText(),
                    fatherNameField.getText(),
                    addressField.getText()
            ));
            ScreenService.getInstance().changeView("users");
        } catch (Exception e) {
            clear();
            error.setText(e.getMessage());
        }
    }

    public void clear(){
        nameField.clear();
        nationalNumField.clear();
        fatherNameField.clear();
        addressField.clear();
    }
}
