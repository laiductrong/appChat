package Service;


import Model.ClientModel;
import Utils.Client;
import com.example.appchat.ChatController;
import com.example.appchat.Controller;
import com.example.appchat.HelloApplication;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Service extends Controller {
    static String un;
    static Socket socket;
    public static ClientModel clientModel;

    public String sendName(String username, int status) throws IOException {
        String reponse = "";
        if (status == 1) {
            String usernameTemp = UUID.randomUUID().toString();
            socket = new Socket("localhost", 5678);
            clientModel = new ClientModel(socket, usernameTemp);
            clientModel.bufferedWriter.write(username);
            clientModel.bufferedWriter.newLine();
            clientModel.bufferedWriter.flush();
            reponse = clientModel.bufferedReader.readLine();
            clientModel.username = username;
        }
        if (status == 2) {
            System.out.println(clientModel.username + " :" + username);
            clientModel.bufferedWriter.write(clientModel.username + " : " + username);
            clientModel.bufferedWriter.newLine();
            clientModel.bufferedWriter.flush();
//            reponse = clientModel.bufferedReader.readLine();
            if (reponse.equals("")) {
                reponse = "oke";
            }
        }
        return reponse;
    }

    public static HBox insertMessToBox(String msg) {
        return new HBox() {{
            Label my_message = new Label(msg);
            getChildren().add(my_message);
            setAlignment(Pos.BASELINE_LEFT);
        }};
    }

    public Runnable listenForMessage() {
        final List<String> mss = null;
        return new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = clientModel.bufferedReader.readLine();
                        String finalMsgFromGroupChat = msgFromGroupChat;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                FXMLLoader loader = getLoader("chat.fxml");
                                try {
                                    HelloApplication.scene.setRoot(loader.load());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                ChatController chatController = (ChatController) loader.getController();
                                HBox nameMessage = insertMessToBox(finalMsgFromGroupChat);
                                ChatController.messages.add(nameMessage);
                                chatController.vBoxMess.getChildren().addAll(ChatController.messages);
//                                System.out.println(chatController.vBoxMess.getChildren());
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeEverything(socket, clientModel.bufferedReader, clientModel.bufferedWriter);
                    }

                }
            }
        };
    }

    public void sendMessage(String messageToSend) {
        try {
                clientModel.bufferedWriter.write(clientModel.username+" : "+messageToSend);
                clientModel.bufferedWriter.newLine();
                clientModel.bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything(socket,clientModel.bufferedReader,clientModel.bufferedWriter);
        }
    }
     public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
