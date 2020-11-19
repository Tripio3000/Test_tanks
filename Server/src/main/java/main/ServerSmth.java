package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.SortedMap;

public class ServerSmth extends Thread {
    private Socket socket;
    private boolean isRunning = false;
    private long id;

    public ServerSmth(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
        start();
    }

    private void send (String str) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(str);
        } catch (IOException e) {}
    }

    @Override
    public void run() {
        super.run();
        String str;
        System.out.println("This id: " + id);
        try {
            Scanner scan = new Scanner(socket.getInputStream());
            send("Init");
            isRunning = true;
            while (scan.hasNext()) {
                str = scan.nextLine();
                if (str.startsWith("ESC")) {
                    send("ESC");
                    socket.close();
                    break;
                }
                else if (str.startsWith("EOG")) {
                    send("EOG");
                    isRunning = false;
                    continue;
                }
                else if (!isRunning) {
                    if (str.startsWith("ESC")) {
                        send("ESC");
                        socket.close();
                    }
                    continue;
                }
                else if (id == 2) {
                    if (str.startsWith("PlRight")) {
                        str = "EnRight";
                    }
                    else if (str.startsWith("PlLeft")) {
                        str = "EnLeft";
                    }
                    else if (str.startsWith("PlShot")) {
                        str = "EnShot";
                    }
                }
                System.out.println(str + "   id: " + id);
                for (ServerSmth s : Server.serverList) {
                    if (s.id == 1 && str.startsWith("PlLeft")) {
                        s.send("PlLeft");
                    }
                    else if (s.id == 2 && str.startsWith("PlLeft")) {
                        s.send("EnRight");
                    }
                    else if (s.id == 1 && str.startsWith("PlRight")) {
                        s.send("PlRight");
                    }
                    else if (s.id == 2 && str.startsWith("PlRight")) {
                        s.send("EnLeft");
                    }
                    else if (s.id == 1 && str.startsWith("PlShot")) {
                        s.send("PlShot");
                    }
                    else if (s.id == 2 && str.startsWith("PlShot")) {
                        s.send("EnShot");
                    }
                    if (s.id == 2 && str.startsWith("EnLeft")) {
                        s.send("PlLeft");
                    }
                    else if (s.id == 1 && str.startsWith("EnLeft")) {
                        s.send("EnRight");
                    }
                    else if (s.id == 2 && str.startsWith("EnRight")) {
                        s.send("PlRight");
                    }
                    else if (s.id == 1 && str.startsWith("EnRight")) {
                        s.send("EnLeft");
                    }
                    else if (s.id == 2 && str.startsWith("EnShot")) {
                        s.send("PlShot");
                    }
                    else if (s.id == 1 && str.startsWith("EnShot")) {
                        s.send("EnShot");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.exit(0);
    }
}
