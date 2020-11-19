package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ReceiveMessageTask extends Task<Void> {
    Socket socket;
    Scanner sc;
//    private Scene newScene;
    private Group root;
    private Stage primaryStage;
    private PrintWriter printWriter;

    public ReceiveMessageTask(Socket socket, Group root, Stage primaryStage, PrintWriter printWriter) {
        this.socket = socket;
        this.root = root;
        this.primaryStage = primaryStage;
        this.printWriter = printWriter;
    }

    public void rr(ImageView player) {
        System.out.println("rrr");
        Platform.runLater(() -> {
            if (player.getX() + 50 < 960) {
                player.setX(player.getX() + 50);
            }
        });
    }

    public Void call() throws Exception {
        sc = new Scanner(socket.getInputStream());

        Image imagePl = null;
        Image imageEn = null;
        Image imageLifePl = null;
        Image imageLifeEn = null;
        Image imageFail = null;
        try (FileInputStream fileInputStream = new FileInputStream("Client/target/classes/assets/player.png");
             FileInputStream fileInputStream1 = new FileInputStream("Client/target/classes/assets/enemy.png");
             FileInputStream fileInputStream2 = new FileInputStream("Client/target/classes/assets/life.png");
             FileInputStream fileInputStream3 = new FileInputStream("Client/target/classes/assets/life.png");
             FileInputStream fileInputStream4 = new FileInputStream("Client/target/classes/assets/fail.png")) {
            imagePl = new Image(fileInputStream);
            imageEn = new Image(fileInputStream1);
            imageLifePl = new Image(fileInputStream2);
            imageLifeEn = new Image(fileInputStream3);
            imageFail = new Image(fileInputStream4);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView player = new ImageView(imagePl);
        player.setFitWidth(80);
        player.setFitHeight(100);
        ImageView enemy = new ImageView(imageEn);
        enemy.setFitWidth(80);
        enemy.setFitHeight(100);
        ImageView life = new ImageView(imageLifePl);
        life.setFitWidth(300);
        life.setFitHeight(40);
        ImageView lifeEn = new ImageView(imageLifeEn);
        lifeEn.setFitWidth(300);
        lifeEn.setFitHeight(40);
        ImageView Fail = new ImageView(imageFail);
        Fail.setFitWidth(300);
        Fail.setFitHeight(400);

        while (true) {
            String serverMessage = sc.nextLine();
//            System.out.println(serverMessage);
            if (serverMessage != null) {
                if (serverMessage.startsWith("PlShot")) {
                    Platform.runLater(() -> {
                        Image imageBul = null;
                        try (FileInputStream fileInputStream = new FileInputStream("Client/target/classes/assets/playerBullet.png")) {
                            imageBul = new Image(fileInputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ImageView bullet = new ImageView(imageBul);
                        bullet.setY(755);
                        bullet.setX(player.getX() + 39);
                        root.getChildren().add(bullet);

                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2), animation -> {
                            if (bullet.isVisible()) {
                                bullet.setY(bullet.getY() - 1);
                                if (bullet.getY() == 240 && bullet.getX() >= enemy.getX() && bullet.getX() <= (enemy.getX() + 80)) {
                                    root.getChildren().remove(bullet);
                                    if (lifeEn.getFitWidth() - 15 != 0) {
                                        lifeEn.setFitWidth(lifeEn.getFitWidth() - 15);
                                        System.out.println("BOOOOOOOOOOOOOOOOOM kill enemy");
                                    }
                                    else {
                                        root.getChildren().remove(lifeEn);
                                        System.out.println("ENEMY DIE");
                                        printWriter.println("EOG");
//                                        primaryStage.close();
                                    }
                                }
                            }
                        }));
                        timeline.setCycleCount(800);
                        timeline.play();
                    });
                }
                if (serverMessage.startsWith("EnShot")) {
                    Platform.runLater(() -> {
                        Image imageBul = null;
                        try (FileInputStream fileInputStream = new FileInputStream("Client/target/classes/assets/enemyBullet.png")) {
                            imageBul = new Image(fileInputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ImageView bullet = new ImageView(imageBul);
                        bullet.setY(250);
                        bullet.setX(enemy.getX() + 38);
                        root.getChildren().add(bullet);

                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2), animation -> {
                            if (bullet.isVisible()) {
                                bullet.setY(bullet.getY() + 1);
                                if (bullet.getY() == 760 && bullet.getX() >= player.getX() && bullet.getX() <= (player.getX() + 80)) {
                                    root.getChildren().remove(bullet);
                                    if (life.getFitWidth() - 15 != 0) {
                                        life.setFitWidth(life.getFitWidth() - 15);
                                        System.out.println("BOOOOOOOOOOOOOOOOOM kill player");
                                    }
                                    else {
                                        root.getChildren().remove(life);
                                        System.out.println("PLAYER DIE");
                                        printWriter.println("EOG");
//                                        primaryStage.close();
                                    }
                                }
                            }
                        }));
                        timeline.setCycleCount(800);
                        timeline.play();
                    });
                }
                if (serverMessage.startsWith("Init")) {
                    Platform.runLater(() -> {
                        player.setX(460);
                        player.setY(750);
                        enemy.setX(460);
                        enemy.setY(150);
                        life.setY(900);
                        life.setX(50);
                        lifeEn.setY(50);
                        lifeEn.setX(650);
                        root.getChildren().add(life);
                        root.getChildren().add(lifeEn);
                        root.getChildren().add(player);
                        root.getChildren().add(enemy);
                    });
                }
                if (serverMessage.startsWith("PlRight")) {
//                    rr(player);
                    Platform.runLater(() -> {
                        if (player.getX() + 30 < 940) {
                            player.setX(player.getX() + 30);
                        }
                    });
                }
                if (serverMessage.startsWith("PlLeft")) {
                    Platform.runLater(() -> {
                        if (player.getX() - 30 > 0) {
                            player.setX(player.getX() - 30);
                        }
                    });
                }
                if (serverMessage.startsWith("EnRight")) {
                    Platform.runLater(() -> {
                        if (enemy.getX() + 30 < 940) {
                            enemy.setX(enemy.getX() + 30);
                        }
                    });
                }
                if (serverMessage.startsWith("EnLeft")) {
                    Platform.runLater(() -> {
                        if (enemy.getX() - 30 > 0) {
                            enemy.setX(enemy.getX() - 30);
                        }
                    });
                }
                if (serverMessage.startsWith("EOG")) {
                    Platform.runLater(() -> {
                        Fail.setX(350);
                        Fail.setY(300);
                        if (!root.getChildren().contains(Fail)) {
                            root.getChildren().add(Fail);
                        }
                    });
                }
                if (serverMessage.startsWith("ESC")) {
                    Platform.runLater(() -> {
                        primaryStage.close();
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.exit(0);
                    });
                    System.out.println("EXIT");
                    break ;
                }
            }
        }
        System.out.println("end of listener");
        return null;
    }
}
