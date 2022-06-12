package com.example.library;

import com.example.library.exception.SecurityException;
import com.example.library.model.Book;
import com.example.library.service.ScreenService;
import com.example.library.service.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LibraryApplication extends Application {

    @Override
    public void start(Stage stage) {
        stage.setResizable(false);
        Scene scene = new Scene(ScreenService.getInstance().getScreen("login"),
                800,
                600);
        ScreenService.getInstance().start(scene);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        TableCreator.getInstance().createTables();
        launch();
    }
}
