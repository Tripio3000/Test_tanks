package main;

import game.Connect;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Window");
        primaryStage.setWidth(100);
        primaryStage.setHeight(100);

        primaryStage.setScene(new Scene(Connect.ConnectToServer(primaryStage)));
        primaryStage.show();
        System.out.println("Close connect window");
    }
}
