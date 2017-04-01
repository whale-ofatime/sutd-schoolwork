package ps7;

import java.util.concurrent.CountDownLatch;

/**
 * Created by eiros_000 on 26/3/2017.
 */
public class CountDownLatchExercise {

    private static final int size = 20;
    private static final int noOfThreads = 5;

    public static void main(String[] args) {
        String[] testArray = new String[size];
        for (int i=0; i<size; i++) {
            if (Math.random()<0.5) {
                testArray[i] = "P";
            } else {
                testArray[i] = "F";
            }
        }

        for (int i=0; i<size; i++) {
            System.out.print(testArray[i]);
        }
        System.out.println();

        Thread[] threadArray = new Thread[noOfThreads];
        int threadSize = size / noOfThreads;

        final CountDownLatch latch = new CountDownLatch(7);


        for (int i=0; i<noOfThreads; i++) {
            int startIndex = threadSize*i;
            int endIndex = threadSize*(i+1);
            if (endIndex >= size) {
                endIndex = size-1;
            }
            threadArray[i] = new GradeThread(startIndex,endIndex,testArray,latch);
            threadArray[i].start();
        }

        try {
            latch.await();
            for (int i=0; i<noOfThreads; i++) {
                threadArray[i].interrupt();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class GradeThread extends Thread {
    private int startIndex;
    private int finalIndex;
    private String[] grades;
    private CountDownLatch latch;

    public GradeThread(int startIndex, int finalIndex, String[] grades, CountDownLatch latch) {
        this.startIndex = startIndex;
        this.finalIndex = finalIndex;
        this.grades = grades;
        this.latch = latch;
    }

    public void run() {
        for (int i=startIndex; i<finalIndex; i++) {
            if (isInterrupted()) {
                break;
            } else {
                if (grades[i].equals("F")) {
                    latch.countDown();
                    System.out.println("Current count is at " + latch.getCount());
                }
            }
        }
    }
}
