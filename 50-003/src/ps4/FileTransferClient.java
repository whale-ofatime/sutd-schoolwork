package ps4;

import java.io.*;
import java.net.Socket;

/**
 * Created by eiros_000 on 1/3/2017.
 */
public class FileTransferClient {

    public static void main (String[] args){
        String hostName = "localhost";
        int portNumber = 4123;

        try{
            Socket clientSocket = new Socket(hostName,portNumber);
            clientSocket.setSoTimeout(2000);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader filein = new BufferedReader(new FileReader("ftpReceived.txt"));

            String line;
            while((line = filein.readLine())!=null){
                System.out.println(line);
                out.println(line);
                try{
                    in.readLine(); //blocks till time out
                }catch (java.net.SocketTimeoutException e){
                    System.out.println("Retransmitting");
                    out.println(line);
                }
            }

            out.close();
            in.close();
            filein.close();
            clientSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}