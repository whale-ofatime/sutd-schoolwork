package ps4;
import java.io.*;
import java.net.*;

/**
 * Created by eiros_000 on 21/2/2017.
 */
public class ChatClient {
    public static void main(String[] args) throws Exception{
        String hostName = "localhost";
        int portNumber = 4321;

        Socket echoSocket = new Socket();
        SocketAddress sockAddr = new InetSocketAddress(hostName,portNumber);
        echoSocket.connect(sockAddr, 100);
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(),true);
        BufferedReader in  = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String userInput;
        do {
            userInput = stdIn.readLine();
            out.println(userInput);
            out.flush();
            System.out.println("Reply from server: " + in.readLine());
        } while (!userInput.equals("End"));

        echoSocket.close();
        in.close();
        out.close();
        stdIn.close();
    }
}