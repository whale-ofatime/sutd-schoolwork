package ps4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by eiros_000 on 27/2/2017.
 */
public class ChatRoom {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1111);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                Socket newClient = serverSocket.accept();
                System.out.println("A new client has joined!");
                Thread newThread = new chatThread(newClient);
                newThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class chatThread extends Thread {
    private Socket socket;
    private BufferedReader in;

    public chatThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while (true) {
                String message = in.readLine();
                if (message.equals("End")){
                    System.out.println("A client has left");
                    break;
                }
                System.out.println(message);
            }
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
