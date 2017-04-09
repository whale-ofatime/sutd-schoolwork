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

        while (true) {
            exec.execute(task);
            int numThreads = 0;
            if (exec instanceof ThreadPoolExecutor) {
                numThreads = ((ThreadPoolExecutor) exec).getActiveCount();
            }
            System.out.println("Current number of active threads: " + numThreads);
        }

        //hint: you can use the following code to get the number of active threads in a thread pool
        /*int numThreads = 0;
        if (exec instanceof ThreadPoolExecutor) {
        	numThreads = ((ThreadPoolExecutor) exec).getActiveCount();
        }*/
//        exec.shutdownNow();
//        exec.shutdown();
    }

    public static void main(String[] args) {
        TestThreadPool test = new TestThreadPool();
        try {
            test.testPoolExpansion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
