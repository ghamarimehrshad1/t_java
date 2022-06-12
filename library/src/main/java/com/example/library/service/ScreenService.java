package com.example.library.service;

import com.example.library.LibraryApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ScreenService {
    private ScreenService(){}
    private static final ScreenService instance = new ScreenService();
    public static ScreenService getInstance() {
        return instance;
    }

    private Scene main;
    private Pane centerView;

    public void start(Scene main) {
        if (this.main == null)
            this.main = main;
    }

    public void setCenter(Pane view){
        centerView = view;
    }

    public Parent getScreen(String name){
        try {
            return new FXMLLoader(
                    LibraryApplication.class.getResource(name+"-view.fxml")
            ).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void activate(String name) {
        main.setRoot(getScreen(name));
    }
    public void changeView(String view) {
        centerView.getChildren().clear();
        centerView.getChildren().add(getScreen(view));
    }
}
