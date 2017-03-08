package ps5;

/**
 * Created by user on 6/3/2017.
 */
public class MultiThreadSearch {
    static int[] testArray = new int[100];
    static int midpoint = testArray.length/2;
    static int itemToBeSearched = 82;
    static boolean isFound = false;
    public static void main(String[] args) {
        for (int i=0;i<100;i++) {
            testArray[i] = i;
        }

        LeftThread leftThread = new LeftThread();
        RightThread rightThread = new RightThread();
        leftThread.start();
        rightThread.start();

        while (true) {
            if (isFound) {
                leftThread.interrupt();
                rightThread.interrupt();
                break;
            }
        }

        System.out.println("End");
    }
}

class LeftThread extends Thread {
    public void run() {
        for (int i=0;i<MultiThreadSearch.midpoint;i++) {
            if (MultiThreadSearch.testArray[i] == MultiThreadSearch.itemToBeSearched) {
                System.out.println("Found in Left thread");
                MultiThreadSearch.isFound = true;
                break;
            } else {
                System.out.println("Not found in Left thread");
            }
        }
    }
}

class RightThread extends Thread {
    public void run() {
        for (int i=MultiThreadSearch.midpoint;i<MultiThreadSearch.testArray.length;i++) {
            if (MultiThreadSearch.testArray[i] == MultiThreadSearch.itemToBeSearched) {
                System.out.println("Found in Right thread");
                MultiThreadSearch.isFound = true;
                break;
            } else {
                System.out.println("Not found in Right thread");
            }
        }
    }
}