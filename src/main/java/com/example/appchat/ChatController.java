package com.example.appchat;

import Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ChatController  implements Initializable {
    public static List<HBox> messages= new ArrayList<>();
    @FXML
    public VBox vBoxMess;
    @FXML
    public TextField textFieldMess;
    @FXML
    public void sendMess(ActionEvent event) {
        String message=textFieldMess.getText();
        if(!message.equals("")){
            service.sendMessage(message);
        }
        vBoxMess.getChildren().addAll(Service.insertMessToBox(message));
        textFieldMess.setText("");
    }

    @FXML
    public void closeChat(ActionEvent event) {
        System.out.println("close");
    }

    public Service service;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetNameController setNameController = new SetNameController();
        this.service =setNameController.setNameService;
//        vBoxMess.getChildren().addAll(Service.insertMessToBox("hhhhh"));
    }
}
