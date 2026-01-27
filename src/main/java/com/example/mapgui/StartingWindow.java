package com.example.mapgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartingWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartingWindow.class.getResource("starting-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Choose Program Window");
        //stage.setWidth(600);
        //stage.setHeight(600);
        stage.setScene(scene);
        stage.show();
    }
}
