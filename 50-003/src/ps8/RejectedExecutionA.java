package ps8;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by user on 2/4/2017.
 */
public class RejectedExecutionA {
    private static final ExecutorService exec = new ScheduledThreadPoolExecutor (100);

    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(4321);

        while (!exec.isShutdown()) {
            try {
                final Socket connection = socket.accept();
                Runnable task = new Runnable () {
                    public void run() {
                        handleRequest(connection);
                    }
                };

                exec.execute(task);
            } catch (RejectedExecutionException e) {
                if (!exec.isShutdown()) {
                    System.out.println("LOG: task submission is rejected.");
                } else {
                    System.out.println("Exec has been shut down");
                }
            }
        }
    }

    public static void stop() {
        exec.shutdownNow();
    }

    protected static void handleRequest(Socket connection) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
            String input = in.readLine();
            if (input.equals("stop")) {
                stop();
            } else {
                BigInteger number = new BigInteger(input);
                BigInteger result = factor(number);
                //System.out.println("sending results: " + String.valueOf(result));
                out.println(result);
                out.flush();
            }
            in.close();
            out.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Something went wrong with the connection");
        }
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
