package queue;

import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class AbstractQueue implements Queue {

    protected int size;

    public void enqueue(Object element) {
        Objects.requireNonNull(element);

        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public Object element() {
        checkCorrectSize();
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public Object dequeue() {
        checkCorrectSize();

        Object result = elementImpl();
        size--;
        removeFront();
        return result;
    }

    protected abstract void removeFront();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        while (size != 0) {
            dequeue();
        }
    }

    public void push(Object element) {
        Objects.requireNonNull(element);

        pushImpl(element);
        size++;
    }

    protected abstract void pushImpl(Object element);

    public Object peek() {
        checkCorrectSize();

        return peekImpl();
    }

    protected abstract Object peekImpl();

    public Object remove() {
        checkCorrectSize();

        Object result = peekImpl();
        size--;
        removeRare();
        return result;
    }

    protected abstract void removeRare();

    protected void checkCorrectSize() {
        if (size <= 0) {
            throw new NoSuchElementException();
        }
    }

    public boolean contains(Object element) {
        Objects.requireNonNull(element);
        return getContainsLink(element) != null;
    }

    public boolean removeFirstOccurrence(Object element) {
        Objects.requireNonNull(element);

        Object temp = getContainsLink(element);
        if (temp == null) {
            return false;
        }

        if (isFront(temp)) {
            dequeue();
        } else if (isRare(temp)) {
            remove();
        } else {
            deleteCentral(temp);
        }
        return true;
    }

    protected abstract boolean isFront(Object element);

    protected abstract boolean isRare(Object element);

    protected abstract Object getContainsLink(Object element);

    protected abstract void deleteCentral(Object element);

}
