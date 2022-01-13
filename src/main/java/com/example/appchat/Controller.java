package com.example.appchat;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

class AppConfig{
    public static AppConfig insTance=new AppConfig();
    public static AppConfig getInstance(){
        return insTance;
    }
}
public class Controller {
    protected  AppConfig APP_CONFIG = AppConfig.getInstance();

    protected FXMLLoader getLoader(String view) {
        return new FXMLLoader(HelloApplication.class.getResource(view));
    }

    protected <T> T getComp(String view) {
        FXMLLoader loader = getLoader(view);
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected <T> T getController(String view) {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(view));
        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return loader.getController();
    }
}

