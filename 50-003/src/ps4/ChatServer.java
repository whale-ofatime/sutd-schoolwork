package ps4;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by eiros_000 on 21/2/2017.
 */
public class ChatServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4321);
            int noOfClients;

            Scanner reader = new Scanner(System.in);
            System.out.println("Enter the number of desired clients: ");
            noOfClients = reader.nextInt();

            System.out.println("... Currently connecting ...");

            ArrayList<Socket> socketList = new ArrayList<>();
            ArrayList<PrintWriter> outList = new ArrayList<>();
            ArrayList<BufferedReader> inList = new ArrayList<>();
            for(int i=0; i<noOfClients; i++) {
                Socket clientSocket = serverSocket.accept();
                socketList.add(clientSocket);
                outList.add(new PrintWriter(clientSocket.getOutputStream(), true));
                inList.add(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
            }
            System.out.println("... Connection established ...");

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            Boolean endRequest = false;
            String inputLine;

            while(!endRequest) {
                for(int i=0; i<noOfClients; i++) {
                    inputLine = inList.get(i).readLine();
                    if (inputLine.equals("End")) {
                        endRequest = true;
                    } else {
                        System.out.println("Reply from client " + (i+1) + ": " + inputLine);
                        outList.get(i).println(stdIn.readLine());
                        outList.get(i).flush();
                    }
                }
            }

            for(int i=0; i<noOfClients; i++) {
                socketList.get(i).close();
                outList.get(i).close();
                inList.get(i).close();
            }
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("... Connection lost ...");
            e.printStackTrace();
        }
    }
}

