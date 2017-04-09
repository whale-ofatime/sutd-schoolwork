package ps9;

import java.util.concurrent.*;
import junit.framework.TestCase;

/**
 * Created by eiros_000 on 6/4/2017.
 */
public class TestThreadPool extends TestCase {
    public void testPoolExpansion() throws InterruptedException {
        int max_pool_size = 10;
        ExecutorService exec = Executors.newFixedThreadPool(max_pool_size);

        //todo: insert your code here to complete the test case
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("This thread is running...");
                    Thread.sleep(5000);
                    System.out.println("This thread has stopped");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        int numThreads = 0;

        long startTime = System.currentTimeMillis();
        long currentTime = startTime;

        while ((currentTime - startTime) < 10000) {
            exec.execute(task);
            if (exec instanceof ThreadPoolExecutor) {
                numThreads = ((ThreadPoolExecutor) exec).getActiveCount();
            }
            System.out.println("Current number of active threads: " + numThreads);

            if (numThreads <= 10) {
                currentTime = System.currentTimeMillis();
                Thread.sleep(500);
            } else {
                break;
            }
        }

        exec.shutdown();
        assertTrue(numThreads <= 10);

    }
}
