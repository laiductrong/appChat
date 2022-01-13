package com.example.appchat;

import Service.SocketService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Scene scene;
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("set-name.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

//            socketService.listening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}