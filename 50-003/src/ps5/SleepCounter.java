package ps5;

import java.util.Scanner;

/**
 * Created by eiros_000 on 8/3/2017.
 */
public class SleepCounter {
    static Object lock = new Object();
    public static void main(String[] args) {
        InputThread input = new InputThread();
        OutputThread output = new OutputThread();

        input.start();
        output.start();

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        output.interrupt();
        System.out.println("Process has stopped");
    }
}

class InputThread extends Thread {
    public void run() {
        System.out.println("Input thread started");
        Scanner sc = new Scanner(System.in);
        synchronized (SleepCounter.lock) {
            if (sc.next().equals("0")) {
                SleepCounter.lock.notify();
            }
        }
    }
}

class OutputThread extends Thread {
    private int count = 0;
    public void run() {
        System.out.println("Output thread started");
        while (true) {
            System.out.println(count);
            count++;
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Count is interrupted");
                break;
            }
        }


    }
}