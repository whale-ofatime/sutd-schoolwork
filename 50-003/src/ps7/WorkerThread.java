package ps7;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eiros_000 on 26/3/2017.
 */

public class WorkerThread extends Thread {
    private Map<String, Integer> map = null;

    public WorkerThread(Map<String, Integer> map) {
        this.map = map;
    }

    public void run() {
        for (int i=0; i<500000; i++) {
            // Return 2 random integers
            Integer newInteger1 = (int) Math.ceil(Math.random());
            Integer newInteger2 = (int) Math.ceil(Math.random());
            // 1. Attempt to retrieve a random Integer element
            map.get(String.valueOf(newInteger1));
            // 2. Attempt to insert a random Integer element
            map.put(String.valueOf(newInteger2), newInteger2);
        }
    }

    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        WorkerThread thread1 = new WorkerThread(Collections.synchronizedMap(map));
        WorkerThread thread2 = new WorkerThread(new ConcurrentHashMap<String,Integer>());

        long timeStart = System.currentTimeMillis();
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("SynchonizedMap: " + (timeEnd-timeStart) + "ms");

        timeStart = System.currentTimeMillis();
        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timeEnd = System.currentTimeMillis();
        System.out.println("ConcurrentHashMap: " + (timeEnd-timeStart) + "ms");
    }
}