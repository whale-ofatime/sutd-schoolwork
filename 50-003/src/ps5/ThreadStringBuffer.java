package ps5;

/**
 * Created by eiros_000 on 8/3/2017.
 */
public class ThreadStringBuffer extends Thread {
    //StringBuffer is used because it is thread-safe
    private StringBuffer buffer;

    public ThreadStringBuffer(StringBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        synchronized (buffer) {
            for (int i=0;i<100;i++) {
                System.out.println(buffer);
            }
            String value = buffer.toString();
            int charValue = value.charAt(0);
            buffer.setCharAt(0,(char) (charValue+1));
        }
    }

    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer("A");
        Thread thread1 = new ThreadStringBuffer(buffer);
        Thread thread2 = new ThreadStringBuffer(buffer);
        Thread thread3 = new ThreadStringBuffer(buffer);
        thread1.start();
        thread2.start();
        thread3.start();

    }
}
