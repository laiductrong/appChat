package com.example.appchat;

import Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SetNameController implements Initializable {
//    public SocketService socketService= SocketService.getInstance();
    public static String thongBao = "";
    public Stage stage;
    public Scene scene;
    public Parent root;
    public static Service setNameService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNameService=new Service();
//        socketService.listening();
    }

    @FXML
    TextField textName;
    @FXML
    Label notification;

    @FXML
    public void setName(ActionEvent event) {
        thongBao = "";
        String rep = textName.getText();
        try {
            this.thongBao=setNameService.sendName(rep,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (thongBao.equals("not ok")) {
            notification.setText("ten da co nguoi su dung");
            textName.setText("");
        } else {
            try {
                root = FXMLLoader.load(getClass().getResource("list-chat.fxml"));
                HelloApplication.scene.setRoot(root);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
