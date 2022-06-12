package com.example.library.controller;

import com.example.library.service.AuthService;
import com.example.library.service.ScreenService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {
    public Pane content;

    @FXML private void handleShowView(ActionEvent e) {
        String view = (String) ((Node)e.getSource()).getUserData();
        ScreenService.getInstance().changeView(view);
    }
    @FXML private void signOut(){
        AuthService.getInstance().logout();
        ScreenService.getInstance().activate("login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScreenService.getInstance().setCenter(content);
        content.getChildren().clear();
        content.getChildren().add(ScreenService.getInstance().getScreen("books"));
    }
}
