package ps4;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

/**
 * Created by eiros_000 on 28/2/2017.
 */
public class ChatRoomClient {
    public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 1111;

        Socket socket = new Socket();
        SocketAddress sockAddr = new InetSocketAddress(hostName,portNumber);

        String message;
        Scanner sc = new Scanner(System.in);

        try {
            socket.connect(sockAddr,1000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            message = "";
            do {
                if (sc.hasNextLine()) {
                    message = sc.nextLine();
                    out.println(message);
                    out.flush();
                    message = "";
                }
            } while (!message.equals("End"));
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
