package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class Server {
    private static int port = 8081;
    public static LinkedList<ServerSmth> serverList = new LinkedList<ServerSmth>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("Server start working!");
            System.out.println("Number of players: 0");
            for (int i = 1; i <= 2; i++) {
                Socket socket = server.accept();

                serverList.add(new ServerSmth(socket, i));

                if (serverList.size() == 2) {
                    for (ServerSmth s : serverList) {
                        s.setReady(true);
                    }
                }
                System.out.println("Number of players: " + i);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.close();
        }
    }
}