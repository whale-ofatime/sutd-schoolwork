package ps4;
import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by eiros_000 on 21/2/2017.
 */
public class ChatServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4321);
            System.out.println("... Currently connecting ...");

            ArrayList<Socket> socketList = new ArrayList<>();
            ArrayList<PrintWriter> outList = new ArrayList<>();
            ArrayList<BufferedReader> inList = new ArrayList<>();
            for(int i=0; i<5; i++) {
                Socket clientSocket = serverSocket.accept();
                socketList.add(clientSocket);
                outList.add(new PrintWriter(clientSocket.getOutputStream(), true));
                inList.add(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
            }
            System.out.println("... Connection established ...");

//            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String inputLine;

//            while (!((inputLine = in.readLine()).equals("End"))) {
//                System.out.println("Reply from client: " + inputLine);
//                out.println(stdIn.readLine());
//                out.flush();
//            }

            Boolean allClientsReady = false;
            Boolean endRequest = false;

            while(!endRequest) {
                for(int i=0; i<5; i++) {}
            }

            for(int i=0; i<5; i++) {
                socketList.get(i).close();
                outList.get(i).close();
                inList.get(i).close();
            }
//            out.println();
            serverSocket.close();
//            clientSocket.close();
//            out.close();
//            in.close();
        } catch (IOException e) {
            System.out.println("... Connection lost ...");
            e.printStackTrace();
        }
    }
}

