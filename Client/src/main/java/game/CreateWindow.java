package game;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class CreateWindow {
    private Stage primaryStage;
    private Scene newScene;
    private Group root;

    public CreateWindow(Stage primaryStage, Scene scene, Group root) {
        this.primaryStage = primaryStage;
        this.newScene = scene;
        this.root = root;
    }

    public void create(String ip, int port) throws IOException {
//        Socket socket = null;
//        Scanner sc = null;
//        try {
//            socket = new Socket(ip, port);
//            sc = new Scanner(socket.getInputStream());
//            String str = sc.nextLine();
//            System.out.println(str);
//        } catch (IOException e) {
//            e.fillInStackTrace();
//        }


//        Group root = new Group();
//        Scene newScene = new Scene(root);
//        CreateWindow win = new CreateWindow(primaryStage, newScene, root);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Tanks");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(1000);
//        primaryStage.setResizable(false);

//        FileInputStream fileInputStream = new FileInputStream("Client/target/classes/assets/player.png");
//        Image imagePl = new Image(fileInputStream);
//
//        ImageView Player = new ImageView(imagePl);
//        Player.setFitWidth(80);
//        Player.setFitHeight(100);
//        Player.setY(500);
//        Player.setX(300);
//        fileInputStream.close();


        root.getChildren().add(field(1000, 1000));
//        root.getChildren().add(box(300, 50, 650, 50));
//        root.getChildren().add(box(300, 50, 50, 900));
//        root.getChildren().add(Player);
        primaryStage.show();
        System.out.println("Close game window");
    }

    public ImageView field(int w, int h) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("Client/target/classes/assets/field.png");
        Image imageFon = new Image(fileInputStream);
        ImageView Field = new ImageView(imageFon);
        Field.setFitWidth(w);
        Field.setFitHeight(h);
        fileInputStream.close();
        return Field;
    }

    public ImageView box(int w, int h, int pos_x, int pos_y) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("Client/target/classes/assets/border.png");
        Image imageBorder = new Image(fileInputStream);
        ImageView Border = new ImageView(imageBorder);
        Border.setFitWidth(w);
        Border.setFitHeight(h);
        Border.setX(pos_x);
        Border.setY(pos_y);
        fileInputStream.close();
        return Border;
    }

//    public ImageView player(int w, int h) throws FileNotFoundException {
//        Image imageTank = new Image(new FileInputStream("target/classes/assets/player.png"));
//        ImageView Tank_pl = new ImageView(imageTank);
//        Tank_pl.setFitWidth(w);
//        Tank_pl.setFitHeight(h);
//
//        return Tank_pl;
//    }
}
