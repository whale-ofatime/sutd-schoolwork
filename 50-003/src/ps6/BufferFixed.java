package ps6;

import java.util.Random;

/**
 * Created by eiros_000 on 13/3/2017.
 */
public class BufferFixed {
    public static void main (String[] args) throws Exception {
        Buffer buffer = new Buffer (10);
        MyProducer prod = new MyProducer(buffer);
        prod.start();
        MyConsumer cons = new MyConsumer(buffer);
        cons.start();
    }
}

class MyProducer extends Thread {
    private Buffer buffer;

    public MyProducer (Buffer buffer) {
        this.buffer = buffer;
    }

    public void run () {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buffer.addItem(new Object());
    }
}

class MyConsumer extends Thread {
    private Buffer buffer;

    public MyConsumer (Buffer buffer) {
        this.buffer = buffer;
    }

    public void run () {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buffer.removeItem();
    }
}

class Buffer {
    public int SIZE;
    private Object[] objects;
    private int count = 0;

    public Buffer (int size) {
        SIZE = size;
        objects = new Object[SIZE];
    }

    public void addItem (Object object) {
        synchronized (this) {
            while (count >= SIZE) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            objects[count] = object;
            count++;
            notifyAll();
        }
    }

    public Object removeItem() {
        synchronized (this) {
            while (count <= 0) {
//                return null;
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count--;
            notifyAll();
            return objects[count];
        }
    }
}