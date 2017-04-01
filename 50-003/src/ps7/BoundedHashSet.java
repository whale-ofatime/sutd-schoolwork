package ps7;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by eiros_000 on 26/3/2017.
 */
public class BoundedHashSet<T> {

    private final Set<T> set;
    private final Semaphore empty;

    public BoundedHashSet (int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        this.empty = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        empty.acquire();
        try {
            boolean status = set.add(o);
            if (!status) {
                empty.release();
            }
        } catch (Exception e) {
            empty.release();
            return false;
        }
        return true;
    }

    public boolean remove (Object o) {
        try {
            boolean status = set.remove(o);
            if (status) {
                empty.release();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}