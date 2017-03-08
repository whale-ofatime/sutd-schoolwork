package ps4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by eiros_000 on 28/2/2017.
 */

public class ChatRoom2 {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(2222);
            serverSocket.setSoTimeout(10000);

            ArrayList<Socket> socketList = new ArrayList<>();
            ArrayList<Thread> threadList = new ArrayList<>();
            ArrayList<String> prevMessageList = new ArrayList<>();

            System.out.println("Currently connecting...");

            while (true) {
                try {
                    Socket newClient = serverSocket.accept();
                    Thread newThread = new chatThread2(newClient);
                    newThread.start();
                    threadList.add(newThread);
                    System.out.println("A new client has joined the chat room");
                    prevMessageList.add("");
                } catch (SocketTimeoutException e) {
                    System.out.println("Timed out");
                    break;
                }
            }

            while (true) {
//                System.out.println("looping");
                for (int i=0; i<threadList.size(); i++) {
                    String message = threadList.get(i).toString();
                    if (!message.equals("null")) {
                        if (!message.equals(prevMessageList.get(i))) {
                            System.out.println("Client " + (i+1) + ": " + message);
                            prevMessageList.set(i,message);
                        } else {
                            System.out.println("checked");
                        }
                    }
//                    System.out.println("Client " + (i+1) + ": " + threadList.get(i).toString());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class chatThread2 extends Thread {
    private Socket socket;
    private BufferedReader in;
    private String recentMessage = "null";

    public chatThread2(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while (true) {
                this.recentMessage = in.readLine();
                if (recentMessage.equals("End")){
                    System.out.println("A client has left");
                    break;
                }
            }
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String toString(){
        return this.recentMessage;
    }
}
