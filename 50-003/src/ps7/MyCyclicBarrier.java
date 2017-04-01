package ps7;

/**
 * Created by eiros_000 on 26/3/2017.
 */
public class MyCyclicBarrier {
    private int count = 0;
    private Runnable torun;

    public MyCyclicBarrier (int count, Runnable torun) {
        this.count = count;
        this.torun = torun;
    }

    public MyCyclicBarrier (int count) {
        this.count = count;
    }

    //complete the implementation below.
    //hint: use wait(), notifyAll()
    public synchronized void await () {
        count--;
        if (count <= 0) {
            torun.run();
            notifyAll();
        }
        while (count > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
