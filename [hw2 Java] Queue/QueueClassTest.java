package queue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

public class QueueClassTest {

    static String ok = "OK";
    static String push = "Your Queue implement method push() incorrect!";
    static String remove = "Your Queue implement method remove() incorrect!";
    static String dequeue = "Your Queue implement method dequeue() incorrect!";
    static String enqueue = "Your Queue implement method enqueue() incorrect!";
    static String toArray = "Your Queue implement method toArray() incorrect!";
    static String clear = "Your Queue implement method clear() incorrect!";

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        arrayQueueModelTest(queue);
    }

    public static void arrayQueueModelTest(ArrayQueue queue) {
        nullTestModule(queue);

        differentTypeTestModule(queue);

        emptyTestModule(queue);

        differentTypeTestModule(queue);

        frontClearTestModule(queue);

        rareClearTestModule(queue);

        toArrayTestModule(queue);

        frontFrontTestModule(queue);

        frontRareTestModule(queue);

        rareFrontTestModule(queue);

        rareRareTestModule(queue);
    }

    private static void nullTestModule(ArrayQueue queue) {
        System.out.print("NullTest Class: ");
        try {
            queue.enqueue(null);
            throw new NullPointerException("Your Queue doesnt' check null in output!");
        } catch (NullPointerException e) {
            System.out.println(ok);
        }
    }

    private static void differentTypeTestModule(ArrayQueue queue) {
        System.out.println("DifferentTypeTest Class: ");
        except(queue, "\tstring: ", new String("Hallo"));
        except(queue, "\tarray: ", generateRandomArray());
        except(queue, "\tdouble: ", new Random().nextDouble());
    }

    private static void except(ArrayQueue queue, String s, Object elem) {
        System.out.print(s);

        queue.push(elem);
        Object out = queue.dequeue();

        if (!out.equals(elem)) {
            throw new IllegalStateException("Incorrect type processing!");
        } else {
            System.out.println(ok);
        }
    }

    private static void emptyTestModule(ArrayQueue queue) {
        System.out.println("EmptyTest Class: ");
        try {
            System.out.print("\tfront check: ");
            queue.dequeue();
            throw new NoSuchElementException("Your Queue try delete first element from empty queue!");
        } catch (NoSuchElementException e) {
            System.out.println(ok);
        }

        try {
            System.out.print("\trare check: ");
            queue.remove();
            throw new NoSuchElementException("Your Queue try delete last element from empty queue!");
        } catch (NoSuchElementException e) {
            System.out.println(ok);
        }
    }

    private static void frontClearTestModule(ArrayQueue queue) {
        System.out.print("Front-Clear Test Class: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            queue.push(a[i]);
            check.addFirst(a[i]);

            if (!queue.element().equals(check.peekFirst())) {
                throw new IllegalStateException(push);
            }
        }

        queue.clear();
        check.clear();

        if (!Arrays.equals(queue.toArray(), check.toArray())) {
            throw new IllegalStateException(clear);
        }

        System.out.println(ok);
    }

    private static void rareClearTestModule(ArrayQueue queue) {
        System.out.print("Rare-Clear Test Class: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            queue.enqueue(a[i]);
            check.addLast(a[i]);

            if (!queue.peek().equals(check.peekLast())) {
                throw new IllegalStateException(enqueue);
            }
        }

        queue.clear();
        check.clear();

        if (!Arrays.equals(queue.toArray(), check.toArray())) {
            throw new IllegalStateException(clear);
        }
        System.out.println(ok);
    }

    private static void toArrayTestModule(ArrayQueue queue) {
        System.out.print("toArrayText Class: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            queue.enqueue(a[i]);
            check.add(a[i]);
        }

        if (Arrays.equals(queue.toArray(), check.toArray())) {
            System.out.println(ok);
            queue.clear();
        } else {
            throw new IllegalStateException(toArray);
        }
    }

    private static void frontFrontTestModule(ArrayQueue queue) {
        System.out.print("Front-Front Test Class: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            queue.push(a[i]);
            check.addFirst(a[i]);

            if (!queue.element().equals(check.peekFirst())) {
                throw new IllegalStateException(push);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!queue.element().equals(check.peekFirst())) {
                throw new IllegalStateException(dequeue);
            }

            queue.dequeue();
            check.removeFirst();
        }

        if (!queue.isEmpty()) {
            throw new IllegalStateException(dequeue);
        }

        System.out.println(ok);
    }

    private static void frontRareTestModule(ArrayQueue queue) {
        System.out.print("Front-Rare Test Class: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            queue.push(a[i]);
            check.addFirst(a[i]);

            if (!queue.element().equals(check.peekFirst())) {
                throw new IllegalStateException(push);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!queue.element().equals(check.element())) {
                throw new IllegalStateException(remove);
            }

            queue.remove();
            check.removeLast();
        }

        if (!queue.isEmpty()) {
            throw new IllegalStateException(remove);
        }

        System.out.println(ok);
    }

    private static void rareFrontTestModule(ArrayQueue queue) {
        System.out.print("Rare-Front Test Class: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            queue.enqueue(a[i]);
            check.addLast(a[i]);

            if (!queue.peek().equals(check.peekLast())) {
                throw new IllegalStateException(push);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!queue.element().equals(check.peekFirst())) {
                throw new IllegalStateException(dequeue);
            }

            queue.dequeue();
            check.removeFirst();
        }

        if (!queue.isEmpty()) {
            throw new IllegalStateException(dequeue);
        }

        System.out.println(ok);
    }

    private static void rareRareTestModule(ArrayQueue queue) {
        System.out.print("Rare-Rare Test Class: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            queue.enqueue(a[i]);
            check.addLast(a[i]);

            if (!queue.peek().equals(check.peekLast())) {
                throw new IllegalStateException(enqueue);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!queue.element().equals(check.peekFirst())) {
                throw new IllegalStateException(remove);
            }

            queue.remove();
            check.removeLast();
        }

        if (!queue.isEmpty()) {
            throw new IllegalStateException(remove);
        }

        System.out.println(ok);
    }

    public static Integer[] generateRandomArray() {
        Random random = new Random();
        int n = random.nextInt(100);
        Integer[] array = new Integer[n];

        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }

}