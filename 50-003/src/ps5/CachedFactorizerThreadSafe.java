package ps5;

/**
 * Created by user on 7/3/2017.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class FactorizerUser {
    public static void main (String[] args) {
        CachedFactorizerThreadSafe factorizer = new CachedFactorizerThreadSafe ();
        Thread tr1 = new Thread (new MyRunnable(factorizer));
        Thread tr2 = new Thread (new MyRunnable(factorizer));
        tr1.start();
        tr2.start();

        try {
            tr1.join();
            tr2.join();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(factorizer.getHits());
        System.out.println(factorizer.getCacheHitRatio());

    }
}

class MyRunnable implements Runnable {
    private CachedFactorizerThreadSafe factorizer;

    public MyRunnable (CachedFactorizerThreadSafe factorizer) {
        this.factorizer = factorizer;
    }

    public void run () {
        Random random = new Random ();
        int randVal = random.nextInt(100);
//        System.out.println(randVal);
//        factorizer.factor(random.nextInt(randVal));
//        factorizer.service(randVal);

        for (int i=0;i<1000;i++) {
            factorizer.service(randVal);
        }
        System.out.println(factorizer.getHits());
        System.out.println(factorizer.getCacheHitRatio());

    }
}

public class CachedFactorizerThreadSafe {
    private int lastNumber;
    private List<Integer> lastFactors;
    private long hits;
    private long cacheHits;
    private Object lock = new Object();

    public long getHits () {
        return hits;
    }

    public double getCacheHitRatio () {
        return (double) cacheHits/ (double) hits;
    }

    public synchronized List<Integer> service (int input) {
        List<Integer> factors = null;
        synchronized (lock) {
            ++hits;

            if (input == lastNumber) {
                ++cacheHits;
                factors = new ArrayList<Integer>(lastFactors);
            }
        }

        if (factors == null) {
            factors = factor(input);
            lastNumber = input;
            lastFactors = new ArrayList<Integer>(factors);
        }

        return factors;
    }

    public List<Integer> factor(int n) {
        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }

        return factors;
    }
}