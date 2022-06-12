package com.example.library.controller;

import com.example.library.exception.UsernameAlreadyTakenException;
import com.example.library.model.Admin;
import com.example.library.service.AuthService;
import com.example.library.service.ScreenService;
import com.example.library.service.SqlService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class RegisterController {
    @FXML public TextField nameField;
    @FXML public TextField usernameField;
    @FXML public PasswordField passwordField;
    @FXML public Text error;

    @FXML public void register(){
        try {
            error.setText(null);
            AuthService.getInstance().register(new Admin(
                    nameField.getText(),
                    usernameField.getText(),
                    passwordField.getText()
            ));
            ScreenService.getInstance().activate("admin-panel");
        } catch (SQLException | SecurityException | UsernameAlreadyTakenException e){
            error.setText(e.getMessage());
        } finally {
            SqlService.getInstance().close();
        }
    }

    @FXML public void goToLogin(){
        ScreenService.getInstance().activate("login");
    }
}
