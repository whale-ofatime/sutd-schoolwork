package ps6;

/**
 * Created by eiros_000 on 19/3/2017.
 */
public class MyStackThreadSafe {
    private final int maxSize;
    private long[] stackArray; //guarded by "this"
    private int top; //invariant: top < stackArray.length && top >= -1; guarded by "this"

    //pre-condition: s > 0
    //post-condition: maxSize == s && top == -1 && stackArray != null
    public MyStackThreadSafe(int s) { //Do we need "synchronized" for the constructor? No
        maxSize = s;
        stackArray = new long[maxSize];
        top = -1;
    }

    //pre-condition: top < maxSize
    //post-condition: top element is "j"
    public synchronized void push(long j) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stackArray[++top] = j;
        notifyAll();
    }

    //pre-condition: top > 0
    //post-condition: the top element is removed
    public synchronized long pop() {
        long toReturn;

        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        toReturn = stackArray[top--];
        notifyAll();
        return toReturn;
    }

    //pre-condition: top > 0
    //post-condition: value of top element is returned
    public synchronized long peek() {
        return stackArray[top];
    }

    //pre-condition: true
    //post-condition: the elements are un-changed. the return value is true iff the stack is empty.
    public synchronized boolean isEmpty() {
        return (top == -1);
    }

    //pre-condition: true
    //post-condition: the elements are un-changed. the return value is true iff the stack is full.
    public synchronized boolean isFull() {
        return (top == maxSize - 1);
    }
}