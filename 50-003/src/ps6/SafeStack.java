package ps6;

import java.util.Stack;

/**
 * Created by eiros_000 on 19/3/2017.
 */
class SafeStack1<E> extends Stack<E> {

    private final int maxSize = 5;

    public synchronized E pushIfNotFull(E e) {
        if (size() >= maxSize) {
            return null;
        } else {
            return e;
        }
    }

    public synchronized E popIfNotEmpty() {
        if (isEmpty()) {
            return null;
        } else {
            return pop();
        }
    }
}

class SafeStack2<E> {

    private final Stack stack;
    private final int maxSize = 5;

    public SafeStack2() {
        this.stack = new Stack();
    }

    public synchronized E push(E e) {
        this.stack.push(e);
        return e;
    }

    public synchronized E pushIfNotFull(E e) {
        if (stack.size() >= maxSize) {
            return null;
        } else {
            this.stack.push(e);
            return e;
        }
    }

    public synchronized E pop() {
        return (E) this.stack.pop();
    }

    public synchronized E popIfNotEmpty() {
        if (stack.empty()) {
            return null;
        } else {
            return (E) this.stack.pop();
        }
    }

    public synchronized E peek(){
        return (E) this.stack.peek();
    }

    public synchronized boolean empty(){
        return this.stack.empty();
    }

    public synchronized int search(Object o){
        return this.stack.search(o);
    }
}
