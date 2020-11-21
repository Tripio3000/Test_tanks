package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connect {

    public static FlowPane ConnectToServer (final Stage primaryStage) {
        FlowPane root = new FlowPane();

        Button button = new Button("Connect to server");
        final Label label = new Label("Press button to start game");
        label.setPrefWidth(200);
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                label.setText("Hello!");
                primaryStage.close();
                Stage stage = new Stage();
                Group root = new Group();
                Scene newScene = new Scene(root);
                CreateWindow window = new CreateWindow(stage, newScene, root);
                PrintWriter printWriter = null;
                try {
                    window.create("localhost", 8081);
                    Client client = new Client(stage, newScene);
                    Socket socket = client.startConnection("localhost", 8081);
                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                    ReceiveMessageTask task = new ReceiveMessageTask(socket, root, stage, printWriter);
                    ExecutorService service = Executors.newFixedThreadPool(1);
                    service.execute(task);
                    client.keyListener(socket, printWriter);
                    System.out.println("111");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("afterbutton");

        root.getChildren().add(button);
        root.getChildren().add(label);
        return root;
    }
}
