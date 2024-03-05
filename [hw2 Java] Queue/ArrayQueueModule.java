package queue;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

// Model: a[0]...a[n-1],a[0]... is container for queue, directly queue is between indexes "front" and "rare"
// and "size" is count of elements of directly queue. Every "iterable moving" is left-right.
// I: n >= 0 && forall i = front..rare: a[i] != null
// Let: immutable(first, second): forall i = first..second: a[i] = a'[(front' + i - first) % n]
public class ArrayQueueModule {

    private static int front = 0;
    private static int rare = -1;
    private static int size = 0;
    private static Object[] elements = new Object[2];

    // Pred: x != null
    // Post: n' = n < size + 1 ? 2 * n : n &&
    //       size' = size + 1 &&
    //       rare' = (rare + 1) % n' &&
    //       front' = front &&
    //       a'[(rare + 1) % n'] = x &&
    //       immutable(front, rare)
    public static void enqueue(Object x) {
        Objects.requireNonNull(x);
        ensureCapacity(size + 1);

        rare = (rare + 1) % elements.length;
        elements[rare] = x;
        size++;
    }

    // Pred: true
    // Post: n' = n < size + 1 ? 2 * n : n && immutable(front, rare)
    private static void ensureCapacity(int capacity) {
        if (elements.length < capacity) {
            elements = Arrays.copyOf(elements, elements.length * 2);

            if (rare < front) {
                int k = elements.length / 2 - 1;
                for (int i = 0; i <= rare; i++) {
                    elements[++k] = elements[i];
                    elements[i] = null;
                }
                rare = k;
            }
        }
    }

    // Pred: size > 0
    // Post: R = a[front] && size' = size && n' = n && immutable(front, rare)
    public static Object element() {
        if (size <= 0) {
            throw new NoSuchElementException();
        }

        return elements[front];
    }

    // Pred: size > 0
    // Post: R = a[front] && size' = size - 1 && n' = n && immutable(front, rare)
    public static Object dequeue() {
        if (size <= 0) {
            throw new NoSuchElementException();
        }

        Object result = elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        size--;

        return result;
    }

    // Pred: true
    // Post: R = size && size' = size && n' = n && immutable(front, rare)
    public static int size() {
        return size;
    }

    // Pred: true
    // Post: R = size == 0 ? true : false && size' = size && n' = n && immutable(front, rare)
    public static boolean isEmpty() {
        return size() == 0;
    }

    // Pred: a != null
    // Post: size' = 0 && n' = n && forall i = 0..n'-1: a[i] = null && rare' = -1 && front' = 0
    public static void clear() {
        while (size != 0) {
            elements[front] = null;
            front = (front + 1) % elements.length;
            size--;
        }
        front = 0;
        rare = -1;
    }

    // Pred: x != null
    // Post: n' = n < size + 1 ? 2 * n : n &&
    //       size' = size + 1 &&
    //       rare' = size == 1 ? front : rare &&
    //       front' = front - 1 < 0 ? elements.length - 1 : front - 1 &&
    //       a'[(rare + 1) % n'] = x &&
    //       immutable(front, rare)
    public static void push(Object x) {
        Objects.requireNonNull(x);
        ensureCapacity(size + 1);

        front = front - 1 < 0 ? elements.length - 1 : front - 1;
        size++;

        if (size == 1) {
            rare = front;
        }

        elements[front] = x;
    }

    // Pred: size > 0
    // Post: R = a[rare] && size' = size && n' = n && immutable(front, rare)
    public static Object peek() {
        if (size <= 0) {
            throw new NoSuchElementException();
        }

        return elements[rare];
    }

    // Pred: size > 0
    // Post: R = a[rare] && size' = size - 1 && n' = n && immutable(front, rare)
    public static Object remove() {
        if (size <= 0) {
            throw new NoSuchElementException();
        }

        Object result = elements[rare];
        elements[rare] = null;

        rare = rare - 1 < 0 ? elements.length - 1 : rare - 1;
        size--;

        return result;
    }

    // Pred: a != null
    // Post: such non-cycle Object[size] R: forall i = 0..size: R[i] = a[(front + i) % n] &&
    //       immutable(front, rare) &&
    //       size' = size &&
    //       n' = n &&
    //       front' = front &&
    //       rare' = rare
    public static Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }

        Object[] result = new Object[size];
        for (int j = 0, i = front; j < size; j++) {
            result[j] = elements[i];
            i = (i + 1) % elements.length;
        }

        return result;
    }

}
