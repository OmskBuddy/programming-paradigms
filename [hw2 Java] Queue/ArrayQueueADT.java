package queue;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

// Model: a[0]...a[n-1],a[0]... is container for queue, directly queue is between indexes "front" and "rare"
// and "size" is count of elements of directly queue. Every "iterable moving" is left-right.
// I: n >= 0 && forall i = front..rare: a[i] != null
// Let: immutable(first, second): forall i = first..second: a[i] = a'[(front' + i - first) % n]
public class ArrayQueueADT {

    private int front;
    private int rare;
    private int size;
    private Object[] elements;

    {
        rare = -1;
        elements = new Object[2];
    }

    // Pred: x != null
    // Post: n' = n < size + 1 ? 2 * n : n &&
    //       size' = size + 1 &&
    //       rare' = (rare + 1) % n' &&
    //       front' = front &&
    //       a'[(rare + 1) % n'] = x &&
    //       immutable(front, rare)
    public static void enqueue(ArrayQueueADT queue, Object x) {
        Objects.requireNonNull(x);
        ensureCapacity(queue, queue.size + 1);

        queue.rare = (queue.rare + 1) % queue.elements.length;
        queue.elements[queue.rare] = x;
        queue.size++;
    }

    // Pred: true
    // Post: n' = n < size + 1 ? 2 * n : n && immutable(front, rare)
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (queue.elements.length < capacity) {
            queue.elements = Arrays.copyOf(queue.elements, queue.elements.length * 2);

            if (queue.rare < queue.front) {
                int k = queue.elements.length / 2 - 1;
                for (int i = 0; i <= queue.rare; i++) {
                    queue.elements[++k] = queue.elements[i];
                    queue.elements[i] = null;
                }
                queue.rare = k;
            }
        }
    }

    // Pred: size > 0
    // Post: R = a[front] && size' = size && n' = n && immutable(front, rare)
    public static Object element(ArrayQueueADT queue) {
        if (queue.size <= 0) {
            throw new NoSuchElementException();
        }

        return queue.elements[queue.front];
    }

    // Pred: size > 0
    // Post: R = a[front] && size' = size - 1 && n' = n && immutable(front, rare)
    public static Object dequeue(ArrayQueueADT queue) {
        if (queue.size <= 0) {
            throw new NoSuchElementException();
        }

        Object result = queue.elements[queue.front];
        queue.front = (queue.front + 1) % queue.elements.length;
        queue.size--;

        return result;
    }

    // Pred: true
    // Post: R = size && size' = size && n' = n && immutable(front, rare)
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // Pred: true
    // Post: R = size == 0 ? true : false && size' = size && n' = n && immutable(front, rare)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return size(queue) == 0;
    }

    // Pred: a != null
    // Post: size' = 0 && n' = n && forall i = 0..n'-1: a[i] = null && rare' = -1 && front' = 0
    public static void clear(ArrayQueueADT queue) {
        while (queue.size != 0) {
            queue.elements[queue.front] = null;
            queue.front = (queue.front + 1) % queue.elements.length;
            queue.size--;
        }
        queue.front = 0;
        queue.rare = -1;
    }

    // Pred: x != null
    // Post: n' = n < size + 1 ? 2 * n : n &&
    //       size' = size + 1 &&
    //       rare' = size == 1 ? front : rare &&
    //       front' = front - 1 < 0 ? elements.length - 1 : front - 1 &&
    //       a'[(rare + 1) % n'] = x &&
    //       immutable(front, rare)а
    public static void push(ArrayQueueADT queue, Object x) {
        Objects.requireNonNull(x);
        ensureCapacity(queue, queue.size + 1);

        queue.front = queue.front - 1 < 0 ? queue.elements.length - 1 : queue.front - 1;
        queue.size++;

        if (queue.size == 1) {
            queue.rare = queue.front;
        }

        queue.elements[queue.front] = x;
    }

    // Pred: size > 0
    // Post: R = a[rare] && size' = size && n' = n && immutable(front, rare)
    public static Object peek(ArrayQueueADT queue) {
        if (queue.size <= 0) {
            throw new NoSuchElementException();
        }

        return queue.elements[queue.rare];
    }

    // Pred: size > 0
    // Post: R = a[rare] && size' = size - 1 && n' = n && immutable(front, rare)
    public static Object remove(ArrayQueueADT queue) {
        if (queue.size <= 0) {
            throw new NoSuchElementException();
        }

        Object result = queue.elements[queue.rare];
        queue.elements[queue.rare] = null;

        queue.rare = queue.rare - 1 < 0 ? queue.elements.length - 1 : queue.rare - 1;
        queue.size--;

        return result;
    }

    // Pred: a != null
    // Post: such non-cycle Object[size] R: forall i = 0..size: R[i] = a[(front + i) % n] &&
    //       immutable(front, rare) &&
    //       size' = size &&
    //       n' = n &&
    //       front' = front &&
    //       rare' = rare
    public static Object[] toArray(ArrayQueueADT queue) {
        if (queue.size == 0) {
            return new Object[0];
        }

        Object[] result = new Object[queue.size];
        for (int j = 0, i = queue.front; j < queue.size; j++) {
            result[j] = queue.elements[i];
            i = (i + 1) % queue.elements.length;
        }

        return result;
    }

}
