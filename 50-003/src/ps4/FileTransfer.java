package ps4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by eiros_000 on 1/3/2017.
 */
public class FileTransfer {
    public static final String FILE = "ftpout";

    public static void main(String[] args){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4123);

//            int i = 1;

            while (true) {
                System.out.println("Connecting...");
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                PrintWriter fileout = new PrintWriter(new FileWriter(new File("ftpSending.txt")), true);

//                PrintWriter fileout = new PrintWriter(new FileWriter(new File(FILE + i + ".txt")), true);
//                i++;

                String line;
                while ((line = in.readLine()) != null) {
                    out.println("Received");
                    System.out.println("Received " + line);
                    fileout.println(line);
                }

                System.out.println("File transfer finished");

                fileout.close();
                socket.close();
                in.close();
                out.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}