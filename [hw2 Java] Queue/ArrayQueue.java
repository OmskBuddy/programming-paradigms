package queue;

import java.util.*;
import java.util.stream.Stream;

public class ArrayQueue extends AbstractQueue {

    private Integer front;
    private Integer rare;
    private Object[] elements;

    public ArrayQueue() {
        this.front = 0;
        this.rare = -1;
        this.elements = new Object[2];
    }

    protected void enqueueImpl(Object element) {
        ensureCapacity(size + 1);
        rare = (rare + 1) % elements.length;
        elements[rare] = element;
    }

    private void ensureCapacity(int capacity) {
        if (elements.length < capacity) {
            elements = Arrays.copyOf(elements, elements.length * 2);

            if (rare < front) {
                System.arraycopy(elements, 0,
                        elements, elements.length / 2, rare + 1);
                rare += elements.length / 2;
            }
        }
    }

    protected Object elementImpl() {
        return elements[front];
    }

    protected void removeFront() {
        elements[front] = null;
        front = (front + 1) % elements.length;
    }

    protected void pushImpl(Object element) {
        ensureCapacity(size + 1);
        front = front - 1 < 0 ? elements.length - 1 : front - 1;
        rare = size == 0 ? front : rare;
        elements[front] = element;
    }

    protected Object peekImpl() {
        return elements[rare];
    }

    protected void removeRare() {
        elements[rare] = null;
        rare = rare - 1 < 0 ? elements.length - 1 : rare - 1;
    }

    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }

        Object[] result = new Object[size];

        int len1 = rare < front ? elements.length - front : rare - front + 1;
        int len2 = rare < front ? rare + 1 : 0;

        System.arraycopy(elements, front, result, 0, len1);
        System.arraycopy(elements, 0, result, len1, len2);
        
        return result;
    }

    public Copiable makeCopy() {
        ArrayQueue copy = new ArrayQueue();
        copy.elements = Arrays.copyOf(elements, elements.length);
        copy.size = size;
        copy.front = front;
        copy.rare = rare;
        return copy;
    }

    protected boolean isFront(Object element) {
        return element.equals(front);
    }

    protected boolean isRare(Object element) {
        return element.equals(rare);
    }

    protected Integer getContainsLink(Object element) {
        for (int j = 0, i = front; j < size; j++) {
            if (element.equals(elements[i])) {
                return i;
            }
            i = (i + 1) % elements.length;
        }
        return null;
    }

    protected void deleteCentral(Object element) {
        int temp = (Integer) element;

        if (temp >= front) {
            System.arraycopy(elements, front,
                    elements, front + 1, temp - front);
            front++;
        } else {
            System.arraycopy(elements, temp + 1,
                    elements, temp, rare - temp);
            rare--;
        }

        size--;
    }
}
