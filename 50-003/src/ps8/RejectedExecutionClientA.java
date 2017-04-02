package ps8;

import java.math.BigInteger;

/**
 * Created by user on 2/4/2017.
 */
public class RejectedExecutionClientA {
    public static void main(String[] args) throws Exception {
        int numberOfClients = 1000; //vary this number here
        long startTime = System.currentTimeMillis();
        BigInteger n = new BigInteger("4294967297");
        //BigInteger n = new BigInteger("239839672845043");
        Thread[] clients = new Thread[numberOfClients];

        clients[0] = new Thread(new ClientWithShutdown(n, true));
        clients[0].start();

        for (int i = 1; i < numberOfClients; i++) {
            clients[i] = new Thread(new ClientWithShutdown(n, false));
            clients[i].start();
        }

        for (int i = 0; i < numberOfClients; i++) {
            clients[i].join();
        }
        System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
    }
}