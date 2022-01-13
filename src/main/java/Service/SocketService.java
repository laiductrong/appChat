package Service;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
//import org.tik.anochatclient.AnoChatApplication;
//import org.tik.anochatclient.Constants;
//import org.tik.anochatclient.Utils;
//import org.tik.anochatclient.controllers.ChatController;
//import org.tik.anochatclient.controllers.Controller;
//import org.tik.anochatclient.controllers.MainController;
//import org.tik.anochatclient.core.models.*;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.example.appchat.Controller;

//class SendMessage implements Runnable {
//    private ObjectOutputStream out;
//    private Socket socket;
//
//    public SendMessage(Socket s, ObjectOutputStream o) {
//        this.socket = s;
//        this.out = o;
//    }
//
//    public void run() {
//        try {
//            while (true) {
//
//                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//                System.out.println("route");
//                String route = stdIn.readLine();
//                Map<String, String> headers = new HashMap<>();
//                while (true) {
//                    System.out.println("key");
//                    String key = stdIn.readLine();
//                    if (key.equals(""))
//                        break;
//                    System.out.println("value");
//                    String value = stdIn.readLine();
//                    if (value.equals(""))
//                        break;
//                    headers.put(key, value);
//                }
//                out.writeObject(
//                        HyperEntity.request(route, headers).toJson()
//                );
//                out.flush();
//                if (route.equalsIgnoreCase("bye"))
//                    break;
//            }
//            System.out.println("Client closed connection");
//            out.close();
//            socket.close();
//        } catch (IOException e) {
//        }
//    }
//}

class ReceiveMessage extends Controller implements Runnable {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    public ReceiveMessage(Socket s, ObjectInputStream i, ObjectOutputStream o) {
        this.socket = s;
        this.in = i;
        this.out = o;
    }

    public void run() {
        while (true) {
//                HyperEntity res = HyperEntity.fromJson((String) in.readObject());
//                switch (res.route) {
//                    case HyperRoute.FIND_PARTNER -> {
//                        if (!res.status.equals(HyperStatus.OK)) {
//                            Utils.showAlertFromHypeException(HyperException.fromJson(res.body));
//                            break;
//                        }
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                String partner_username = res.headers.get(Constants.USERNAME);
//                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                                alert.setTitle("Accept partner");
//                                alert.setHeaderText("Accept partner");
//                                alert.setContentText("Chat with %s ?".formatted(partner_username));
//                                Optional<ButtonType> result = alert.showAndWait();
//                                try {
//                                    if (result.get() == ButtonType.OK) {
//                                        out.writeObject(HyperEntity
//                                                .route(HyperRoute.ACCEPT_PARTNER)
//                                                .headers(new HashMap<>() {{
//                                                    put(Constants.IS_ACCEPTED, Constants.YES);
//                                                    put(Constants.USERNAME, partner_username);
//                                                }}).build().toJson());
//                                    } else {
//                                        out.writeObject(HyperEntity
//                                                .route(HyperRoute.ACCEPT_PARTNER)
//                                                .headers(new HashMap<>() {{
//                                                    put(Constants.IS_ACCEPTED, Constants.NO);
//                                                    put(Constants.USERNAME, partner_username);
//                                                }}).build().toJson());
//                                    }
//                                    System.out.println(in.readObject());
//                                } catch (IOException | ClassNotFoundException e) {
//                                }
//
//                            }
//                        });
//                    }
//                    case HyperRoute.ACCEPT_PARTNER -> {
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                String partner_username = res.headers.get(Constants.USERNAME);
//                                if (res.headers.get(Constants.IS_ACCEPTED).equals(Constants.YES)) {
//                                    FXMLLoader loader = getLoader(APP_CONFIG.mainView());
//                                    try {
//                                        AnoChatApplication.scene.setRoot(loader.load());
//                                    } catch (IOException ignore) {
//                                    }
//
//                                    MainController controller = (MainController) loader.getController();
//                                    try {
//                                        controller.mainPane.setCenter(
//                                                new FXMLLoader(
//                                                        AnoChatApplication.class.getResource("chat-pane.fxml")
//                                                ).load()
//                                        );
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                    ChatController.lblPartner.setText(partner_username);
//                                } else {
//                                    Utils.showAlert(
//                                            "%s wasn't accepted".formatted(partner_username),
//                                            "%s wasn't accepted".formatted(partner_username),
//                                            "%s wasn't accepted".formatted(partner_username)
//                                    );
//                                }
//                            }
//                        });
//                    }
//                }
        }
    }
}

public class SocketService extends Socket {
    private static String host = "localhost";
    private static int port = 5678;
    public ObjectOutputStream out;
    public ObjectInputStream in;


    public SocketService() throws IOException {
        super(host, port);
        out = new ObjectOutputStream(getOutputStream());
        in = new ObjectInputStream(getInputStream());
    }

    public void listening() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReceiveMessage rev = new ReceiveMessage(this, in, out);
        executor.execute(rev);
    }


    private static SocketService INSTANCE;

    static {
        try {
            INSTANCE = new SocketService();
        } catch (IOException e) {
//            Utils.showAlertInPlatform("Connect to server failed", null, "Cannot connect to server");
        }
    }

    public static SocketService getInstance() {
        return INSTANCE;
    }
}

