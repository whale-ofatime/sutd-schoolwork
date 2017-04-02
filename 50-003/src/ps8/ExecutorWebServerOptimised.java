package ps8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by user on 2/4/2017.
 */
public class ExecutorWebServerOptimised {
    private static final int NUMBEROFCPU = Runtime.getRuntime().availableProcessors();
    private static final double TARGETCPUUTILIZATION = 0.8;
    private static final double WAITCOMPUTERATIO = 0.05;
    private static final int NTHREADS = (int) Math.round((double)NUMBEROFCPU * TARGETCPUUTILIZATION * (1.0 + WAITCOMPUTERATIO));
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main (String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(4321, 1000);

        long startTime = 0;
        while (true) {
//            Socket connection = socket.accept();
//            if (startTime == 0) {
//                startTime = System.currentTimeMillis();
//            }
//            handleRequest(connection);

            final Socket connection = socket.accept();
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        handleRequest(connection);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(task);
        }
    }

    private static void handleRequest (Socket connection) throws Exception {
        //todo
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
        BigInteger number = new BigInteger(in.readLine());
        BigInteger result = factor(number);
//        System.out.println("sending results: " + String.valueOf(result));
        out.println(result);
        out.flush();
        in.close();
        out.close();
        connection.close();

    }

    private static BigInteger factor(BigInteger n) {
        BigInteger i = new BigInteger("2");
        BigInteger zero = new BigInteger("0");

        while (i.compareTo(n) < 0) {
            if (n.remainder(i).compareTo(zero) == 0) {
                return i;
            }

            i = i.add(new BigInteger("1"));
        }

        assert(false);
        return null;
    }
}