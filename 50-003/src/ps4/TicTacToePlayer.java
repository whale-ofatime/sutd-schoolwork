package ps4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

/**
 * Created by eiros_000 on 1/3/2017.
 */
public class TicTacToePlayer {
    public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 3333;

        Socket socket = new Socket();
        SocketAddress sockAddr = new InetSocketAddress(hostName, portNumber);

        String requestInput = "Please select a spot to place your symbol";
        String invalidInput = "Invalid input, please try again";
        String winnerResult = "You are the winner!";
        String loserResult = "Sorry, you have lost";
        String gridSending = "Sending grid...";
        String gridSent = "Grid sent!";

        try {
            socket.connect(sockAddr,100);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

            // Game begin message
            System.out.println(in.readLine());

            Scanner sc = new Scanner(System.in);

            while (true) {
                String lastInput = in.readLine();
                if (lastInput.equals(requestInput)) {
                    System.out.println(requestInput);
                    out.println(sc.nextLine());
                    out.flush();
                } else if (lastInput.equals(invalidInput)) {
                    System.out.println(invalidInput);
                } else if (lastInput.equals(winnerResult)) {
                    System.out.println(winnerResult);
                    break;
                } else if (lastInput.equals(loserResult)) {
                    System.out.println(loserResult);
                    break;
                } else if (lastInput.equals(gridSending)) {
//                    System.out.println(in.readLine());
                    String received = in.readLine();
                    while (!received.equals(gridSent)) {
                        System.out.println(received);
                        received = in.readLine();
                    }
                }
            }
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
