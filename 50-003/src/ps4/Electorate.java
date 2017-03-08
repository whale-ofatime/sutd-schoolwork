package ps4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by eiros_000 on 1/3/2017.
 */
public class Electorate {
    public static void main(String[] args){
        String hostName = "localhost";
        int portNumber = 4213;
        int countA = 0;
        int countB = 0;

        try{
            Socket clientSocket = new Socket(hostName,portNumber);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            System.out.println("Start voting!");
            String vote = stdIn.readLine();
            while(!vote.equals("A") && !vote.equals("B")){
                System.out.println("Please enter a valid candidate name");
                vote = stdIn.readLine();
            }
            out.println(vote);


            while((vote = in.readLine()) != null){
                System.out.println("Counting votes...");
                if(vote.equals("A"))
                    countA++;
                else if(vote.equals("B"))
                    countB++;
            }

        }catch (IOException e){
            if( e instanceof SocketException){
                if(countA >countB)
                    System.out.println("The winner is A");
                else if (countB >countA)
                    System.out.println("The winner is B");
                else
                    System.out.println("It's a tie!");

            }else
                e.printStackTrace();
        }




    }

}