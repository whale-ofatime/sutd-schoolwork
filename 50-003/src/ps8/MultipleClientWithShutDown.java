package ps8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

/**
 * Created by user on 2/4/2017.
 */
public class MultipleClientWithShutDown {
    public static void main(String[] args) throws Exception {
        int numberOfClients = 1000; //vary this number here
        long startTime = System.currentTimeMillis();
        BigInteger n = new BigInteger("4294967297");
        //BigInteger n = new BigInteger("239839672845043");
        Thread[] clients = new Thread[numberOfClients];

        for (int i = 0; i < numberOfClients; i++) {
            clients[i] = new Thread(new ClientWithShutdown(n,false));
            clients[i].start();
        }

        for (int i = 0; i < numberOfClients; i++) {
            clients[i].join();
        }
        System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
    }
}

class ClientWithShutdown implements Runnable {
    private final BigInteger n;
    private final Boolean shutdown;

    public ClientWithShutdown (BigInteger n, boolean shutdown) {
        this.n = n;
        this.shutdown = shutdown;
    }

    public void run() {
        String hostName = "localhost";
        int portNumber = 4321;

        try {
            //long startTime = System.currentTimeMillis();
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
            if (shutdown) {
                out.println("stop");
            } else {
                out.println(n.toString());
            }
            out.flush();
            in.readLine();
            //System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
            out.close();
            in.close();
            socket.close();
        }
        catch (Exception e) {
            System.out.println("Server got problem");
        }
    }
}