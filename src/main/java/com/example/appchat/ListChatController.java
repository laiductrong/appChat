package com.example.appchat;

import Service.Service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListChatController implements Initializable {
    public Stage stage;
    public Scene scene;
    public Parent root;
    public Service setNameService;
    @FXML
    private Button wantChat;
    @FXML
    private Label namePartner;
    @FXML
    private ListView<String> listUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetNameController setNameController = new SetNameController();
        String danhsach = setNameController.thongBao;
        this.setNameService=setNameController.setNameService;
        danhsach = danhsach.substring(1, danhsach.length() - 1);
        String[] words = danhsach.split(",");

        List<String> hihi = new ArrayList<>();
        for (String w : words) {
            if (w.length()<20)
            hihi.add(w);
        }
        if (hihi.size()>1){
            hihi.remove(hihi.size()-1);
        }

//        user.
        listUser.getItems().addAll(hihi);
        listUser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                String u = listUser.getSelectionModel().getSelectedItems().toString();
                u = u.substring(1, u.length() - 1);
                namePartner.setText(u.trim());
            }
        });
    }

    @FXML
    public void chat(ActionEvent event) {
        String partner=namePartner.getText();
        System.out.println("chuyen");
        System.out.println(partner);
        try {
            String s=setNameService.sendName(partner,2);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ExecutorService service= Executors.newFixedThreadPool(2);
            service.execute(setNameService.listenForMessage());

            root = new FXMLLoader(HelloApplication.class.getResource("chat.fxml")).load();
            HelloApplication.scene.setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void waitOther(ActionEvent event) {
        try {
            ExecutorService service= Executors.newFixedThreadPool(2);
            service.execute(setNameService.listenForMessage());
            root = new FXMLLoader(HelloApplication.class.getResource("chat.fxml")).load();
            HelloApplication.scene.setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
