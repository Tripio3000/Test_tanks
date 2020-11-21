package game;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    private Stage primaryStage;
    private Scene scene;

    public Client(Stage primaryStage, Scene scene) {
        this.primaryStage = primaryStage;
        this.scene = scene;
    }

    public Socket startConnection(String ip, int port) {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            System.out.println("exeption");
            e.fillInStackTrace();
        }
        return socket;
    }

    public void keyListener(Socket socket, PrintWriter printWriter) {
        System.out.println("good!");
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    printWriter.println("PlShot");
//                    System.out.println("SPACE");
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    printWriter.println("PlRight");
//                    System.out.println("LEFT");
                }
                if (event.getCode() == KeyCode.LEFT) {
                    printWriter.println("PlLeft");
//                    System.out.println("RIGHT");
                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    printWriter.println("ESC");
                }
            }
        });
    }
}
