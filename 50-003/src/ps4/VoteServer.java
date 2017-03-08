package ps4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by eiros_000 on 1/3/2017.
 */
public class VoteServer {
    public static void main(String[] args){
        int portNumber = 4213;
        int numOfElectorates = 5;

        try{
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket[] clientSockets = new Socket[numOfElectorates];
            BufferedReader[] inList = new BufferedReader[numOfElectorates];
            PrintWriter[] outList = new PrintWriter[numOfElectorates];

            System.out.println("Connecting...");

            for(int i=0; i<numOfElectorates; i++){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client " + i + " has connected");
                clientSockets[i] = clientSocket;
                inList[i] = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                outList[i] = new PrintWriter(clientSocket.getOutputStream(), true);
            }

            String vote;
            for(int i =0; i<numOfElectorates; i++){
                vote = inList[i].readLine();
                System.out.println("Vote is " + vote);
                if(vote.equals("A")){
                    for(int j =0; j< numOfElectorates; j++ ){
                        outList[j].println("A");
                    }
                }else if (vote.equals("B")){
                    for(int j = 0; j < numOfElectorates; j++){
                        outList[j].println("B");
                    }
                }
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}