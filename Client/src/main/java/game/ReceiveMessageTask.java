package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public void node_left(ImageView node) {
        Platform.runLater(() -> {
            if (node.getX() + 30 < 940) {
                node.setX(node.getX() + 30);
            }
        });
    }
    public void node_right(ImageView node) {
        Platform.runLater(() -> {
            if (node.getX() - 30 > 0) {
                node.setX(node.getX() - 30);
            }
        });
    }

    public void init_image(ImageView image, double w, double h) {
        image.setFitWidth(w);
        image.setFitHeight(h);
    }

    public void end_of_game(ImageView Fail, AtomicBoolean alive) {
        Platform.runLater(() -> {
            final Label label = new Label();
            if (alive.get()) {
                label.setText("WIN");
            }
            else {
                label.setText("FAIL");
            }
            label.setFont(Font.font("Arial", 30));
            Fail.setX(350);
            Fail.setY(200);
            label.setLayoutX(Fail.getX() + 120);
            label.setLayoutY(Fail.getY() + 150);
            if (!root.getChildren().contains(Fail) && !root.getChildren().contains(label)) {
                root.getChildren().add(Fail);
                root.getChildren().add(label);
            }
        });
    }

    public Void call() throws Exception {
        sc = new Scanner(socket.getInputStream());
        AtomicBoolean alive = new AtomicBoolean(true);

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
        init_image(player, 80, 100);
        ImageView enemy = new ImageView(imageEn);
        init_image(enemy, 80, 100);
        ImageView life = new ImageView(imageLifePl);
        init_image(life, 300, 40);
        ImageView lifeEn = new ImageView(imageLifeEn);
        init_image(lifeEn, 300, 40);
        ImageView Fail = new ImageView(imageFail);
        init_image(Fail, 300, 400);

        while (true) {
            String serverMessage = sc.nextLine();
            if (serverMessage != null) {
                if (serverMessage.startsWith("PlShot")) {
                    Platform.runLater(() -> {
                        Image imageBul = null;
                        AtomicBoolean shot = new AtomicBoolean(false);
                        try (FileInputStream fileInputStream = new FileInputStream("Client/target/classes/assets/playerBullet.png")) {
                            imageBul = new Image(fileInputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ImageView bullet = new ImageView(imageBul);
                        bullet.setY(player.getY() + 5);
                        bullet.setX(player.getX() + 39);
                        root.getChildren().add(bullet);

                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), animation -> {
                            if (bullet.isVisible()) {
                                bullet.setY(bullet.getY() - 1);
                                if (!shot.get() && bullet.getY() <= 170 && bullet.getY() >= 70 && bullet.getX() >= enemy.getX() && bullet.getX() <= (enemy.getX() + 80)) {
                                    shot.set(true);
                                    root.getChildren().remove(bullet);
                                    if (lifeEn.getFitWidth() - 15 != 0) {
                                        lifeEn.setFitWidth(lifeEn.getFitWidth() - 15);
                                        System.out.println("boom shot player");
                                    }
                                    else {
                                        root.getChildren().remove(lifeEn);
//                                        alive.set(false);
                                        System.out.println("ENEMY DIE");
                                        printWriter.println("EOGPL");
//                                        primaryStage.close();
                                    }
                                }
                            }
                        }));
                        timeline.setCycleCount(850);
                        timeline.play();
                    });
                }
                if (serverMessage.startsWith("EnShot")) {
                    Platform.runLater(() -> {
                        Image imageBul = null;
                        AtomicBoolean shot = new AtomicBoolean(false);
                        try (FileInputStream fileInputStream = new FileInputStream("Client/target/classes/assets/enemyBullet.png")) {
                            imageBul = new Image(fileInputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ImageView bullet = new ImageView(imageBul);
                        bullet.setY(enemy.getY() + 100);
                        bullet.setX(enemy.getX() + 38);
                        root.getChildren().add(bullet);

                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), animation -> {
                            if (bullet.isVisible()) {
                                bullet.setY(bullet.getY() + 1);
                                if (!shot.get() && bullet.getY() >= 710 && bullet.getY() <= 790 && bullet.getX() >= player.getX() && bullet.getX() <= (player.getX() + 80)) {
                                    shot.set(true);
                                    root.getChildren().remove(bullet);
                                    if (life.getFitWidth() - 15 != 0) {
                                        life.setFitWidth(life.getFitWidth() - 15);
                                        System.out.println("boom shot player");
                                    }
                                    else {
                                        root.getChildren().remove(life);
                                        alive.set(false);
                                        System.out.println("PLAYER DIE");
                                        printWriter.println("EOGPL");
                                    }
                                }
                            }
                        }));
                        timeline.setCycleCount(850);
                        timeline.play();
                    });
                }
                if (serverMessage.startsWith("Init")) {
                    Platform.runLater(() -> {
                        player.setX(460);
                        player.setY(700);
                        enemy.setX(460);
                        enemy.setY(70);
                        life.setY(player.getY() + 120);
                        life.setX(50);
                        lifeEn.setY(enemy.getY() - 60);
                        lifeEn.setX(650);
                        root.getChildren().add(life);
                        root.getChildren().add(lifeEn);
                        root.getChildren().add(player);
                        root.getChildren().add(enemy);
                    });
                }
                if (serverMessage.startsWith("PlRight")) {
                    node_left(player);
                }
                if (serverMessage.startsWith("PlLeft")) {
                    node_right(player);
                }
                if (serverMessage.startsWith("EnRight")) {
                    node_left(enemy);
                }
                if (serverMessage.startsWith("EnLeft")) {
                    node_right(enemy);
                }
                if (serverMessage.startsWith("EOG")) {
                    end_of_game(Fail, alive);
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
        return null;
    }
}
