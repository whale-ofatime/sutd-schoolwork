package ps9;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.*;

/**
 * Created by user on 9/4/2017.
 */
public class BoundedBufferTest {
    private static final long LOCKUP_DETECT_TIMEOUT = 1000;

    @Test
    public void testIsEmptyWhenConstructued () {
        BoundedBufferWithSpec<Integer> bb = new BoundedBufferWithSpec<Integer>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    @Test
    public void testIsFullAfterPuts () throws InterruptedException {
        final BoundedBufferWithSpec<Integer> bb = new BoundedBufferWithSpec<Integer>(10);

        Runnable task = new Runnable () {
            public void run() {
                try {
                    bb.put((new Random()).nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread (task);
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }

        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());
    }

    @Test
    public void testTakeBlocksWhenEmpty () {
        final BoundedBufferWithSpec<Integer> bb = new BoundedBufferWithSpec<Integer>(10);
        Thread taker = new Thread() {
            public void run() {
                try {
                    int unused = bb.take();
                    assertTrue(false);
                } catch (InterruptedException success) {} //if interrupted, the exception is caught here
            }
        };

        try {
            taker.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT);
            taker.interrupt();
            taker.join(LOCKUP_DETECT_TIMEOUT);
            assertFalse(taker.isAlive()); //the taker should not be alive for some time
        } catch (Exception unexpected) {
            assertTrue(false);
        }
    }

    @Test
    public void testPutsBlocksWhenFull() throws InterruptedException {
        final BoundedBufferWithSpec<Integer> bb = new BoundedBufferWithSpec<Integer>(10);

        Runnable task = new Runnable () {
            public void run() {
                try {
                    bb.put((new Random()).nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread (task);
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }

        Thread putter = new Thread() {
            public void run() {
                try {
                    bb.put((new Random()).nextInt());
                    assertTrue(false);
                } catch (InterruptedException success) {} //if interrupted, the exception is caught here
            }
        };

        try {
            putter.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT);
            putter.interrupt();
            putter.join(LOCKUP_DETECT_TIMEOUT);
            assertFalse(putter.isAlive()); //the taker should not be alive for some time
        } catch (Exception unexpected) {
            assertTrue(false);
        }
    }

    @Test
    public void testIsEmptyAfterTake() throws InterruptedException {
        final BoundedBufferWithSpec<Integer> bb = new BoundedBufferWithSpec<Integer>(10);

        Runnable putTask = new Runnable () {
            public void run() {
                try {
                    bb.put((new Random()).nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread[] putThreads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            putThreads[i] = new Thread (putTask);
            putThreads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            putThreads[i].join();
        }

        Runnable takeTask = new Runnable() {
            public void run() {
                try {
                    int take = bb.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread[] takeThreads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            takeThreads[i] = new Thread (takeTask);
            takeThreads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            takeThreads[i].join();
        }

        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());

    }

}