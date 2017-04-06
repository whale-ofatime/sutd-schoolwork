package ps9;

import java.util.Random;

/**
 * Created by eiros_000 on 4/4/2017.
 */
public class DiningPhilFree {
    private static int N = 5;

    public static void main (String[] args) throws Exception {
        PhilosopherFree[] phils = new PhilosopherFree[N];
        ForkFree[] forks = new ForkFree[N];

        for (int i = 0; i < N; i++) {
            forks[i] = new ForkFree(i);
        }

        for (int i = 0; i < N; i++) {
            phils[i] = new PhilosopherFree (i, forks[i], forks[(i+N-1)%N]);
            phils[i].start();
        }
    }
}

class PhilosopherFree extends Thread {
    private final int index;
    private final ForkFree left;
    private final ForkFree right;

    public PhilosopherFree (int index, ForkFree left, ForkFree right) {
        this.index = index;
        this.left = left;
        this.right = right;
    }

    public void run() {
        Random randomGenerator = new Random();
        try {
            while (true) {
                Thread.sleep(randomGenerator.nextInt(100)); //not sleeping but thinking
                System.out.println("Phil " + index + " finishes thinking.");
                left.pickup();
                System.out.println("Phil " + index + " picks up left fork.");
                Thread.sleep(1000);
                right.pickup();
                System.out.println("Phil " + index + " picks up right fork.");
                Thread.sleep(randomGenerator.nextInt(100)); //eating
                System.out.println("Phil " + index + " finishes eating.");
                left.putdown();
                System.out.println("Phil " + index + " puts down left fork.");
                right.putdown();
                System.out.println("Phil " + index + " puts down right fork.");
            }
        } catch (InterruptedException e) {
            System.out.println("Don't disturb me while I am sleeping, well, thinking.");
        }
    }
}

class ForkFree {
    private final int index;
    private boolean isAvailable = true;

    public ForkFree (int index) {
        this.index = index;
    }

    public synchronized void pickup () throws InterruptedException {
        while (!isAvailable) {
            wait();
        }

        isAvailable = false;
        notifyAll();
    }

    public synchronized void putdown() throws InterruptedException {
        while (isAvailable) {
            wait();
        }

        isAvailable = true;
        notifyAll();
    }

    public String toString () {
        if (isAvailable) {
            return "Fork " + index + " is available.";
        }
        else {
            return "Fork " + index + " is NOT available.";
        }
    }
}