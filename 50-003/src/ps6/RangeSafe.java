package ps6;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by eiros_000 on 19/3/2017.
 */
//is this class thread-safe? No. It does not preserve the invariant of lower <= upper.

public class RangeSafe {
    public final AtomicInteger lower = new AtomicInteger(0);
    public final AtomicInteger upper = new AtomicInteger(0);
    private final Object lock = new Object();
    //invariant: lower <= upper

    public void setLower(int i) {
        synchronized (lock) {
            if (i > upper.get()) {
                throw new IllegalArgumentException ("Can't set lower to " + i + " > upper");
            }

            lower.set(i);
        }
    }

    public void setUpper(int i) {
        synchronized (lock) {
            if (i < lower.get()) {
                throw new IllegalArgumentException ("Can't set upper to " + i + " < lower");
            }

            upper.set(i);
        }
    }

    public boolean isInRange(int i) {
        synchronized (lock) {
            return (i >= lower.get()) && i <= upper.get();
        }
    }

    public boolean isValid() {
        synchronized (lock) {
            return lower.get() <= upper.get();
        }
    }
}
