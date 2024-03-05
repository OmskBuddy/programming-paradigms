package queue;

import java.util.*;

public class QueueModuleTest {

    static String ok = "OK";
    static String push = "Your Queue implement method push() incorrect!";
    static String remove = "Your Queue implement method remove() incorrect!";
    static String dequeue = "Your Queue implement method dequeue() incorrect!";
    static String enqueue = "Your Queue implement method enqueue() incorrect!";
    static String toArray = "Your Queue implement method toArray() incorrect!";
    static String clear = "Your Queue implement method clear() incorrect!";

    public static void main(String[] args) {
        arrayQueueModelTest();
    }

    public static void arrayQueueModelTest() {
        nullTestModule();

        differentTypeTestModule();

        emptyTestModule();

        differentTypeTestModule();

        frontClearTestModule();

        rareClearTestModule();

        toArrayTestModule();

        frontFrontTestModule();

        frontRareTestModule();

        rareFrontTestModule();

        rareRareTestModule();
    }

    private static void nullTestModule() {
        System.out.print("NullTest Module: ");
        try {
            ArrayQueueModule.enqueue(null);
            throw new NullPointerException("Your Queue doesnt' check null in input!");
        } catch (NullPointerException e) {
            System.out.println(ok);
        }
    }

    private static void differentTypeTestModule() {
        System.out.println("DifferentTypeTest Module: ");
        except("\tstring: ", new String("Hallo"));
        except("\tarray: ", generateRandomArray());
        except("\tdouble: ", new Random().nextDouble());
    }

    private static void except(String s, Object elem) {
        System.out.print(s);

        ArrayQueueModule.push(elem);
        Object out = ArrayQueueModule.dequeue();

        if (!out.equals(elem)) {
            throw new IllegalStateException("Incorrect type processing!" + System.lineSeparator() +
                    "expected: " + elem + System.lineSeparator() + "actual: " + out);
        } else {
            System.out.println(ok);
        }
    }

    private static void emptyTestModule() {
        System.out.println("EmptyTest Module: ");
        try {
            System.out.print("\tfront check: ");
            ArrayQueueModule.dequeue();
            throw new NoSuchElementException("Your Queue try delete first element from empty queue!");
        } catch (NoSuchElementException e) {
            System.out.println(ok);
        }

        try {
            System.out.print("\trare check: ");
            ArrayQueueModule.remove();
            throw new NoSuchElementException("Your Queue try delete last element from empty queue!");
        } catch (NoSuchElementException e) {
            System.out.println(ok);
        }
    }

    private static void frontClearTestModule() {
        System.out.print("Front-Clear Test Module: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            ArrayQueueModule.push(a[i]);
            check.addFirst(a[i]);

            if (!ArrayQueueModule.element().equals(check.peekFirst())) {
                throw new IllegalStateException(push);
            }
        }

        ArrayQueueModule.clear();
        check.clear();

        if (!Arrays.equals(ArrayQueueModule.toArray(), check.toArray())) {
            throw new IllegalStateException(clear);
        }

        System.out.println(ok);
    }

    private static void rareClearTestModule() {
        System.out.print("Rare-Clear Test Module: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            ArrayQueueModule.enqueue(a[i]);
            check.addLast(a[i]);

            if (!ArrayQueueModule.peek().equals(check.peekLast())) {
                throw new IllegalStateException(enqueue);
            }
        }

        ArrayQueueModule.clear();
        check.clear();

        if (!Arrays.equals(ArrayQueueModule.toArray(), check.toArray())) {
            throw new IllegalStateException(clear);
        }
        System.out.println(ok);
    }

    private static void toArrayTestModule() {
        System.out.print("toArrayText Module: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            ArrayQueueModule.enqueue(a[i]);
            check.add(a[i]);
        }

        if (Arrays.equals(ArrayQueueModule.toArray(), check.toArray())) {
            System.out.println(ok);
            ArrayQueueModule.clear();
        } else {
            throw new IllegalStateException(toArray);
        }
    }

    private static void frontFrontTestModule() {
        System.out.print("Front-Front Test Module: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            ArrayQueueModule.push(a[i]);
            check.addFirst(a[i]);

            if (!ArrayQueueModule.element().equals(check.peekFirst())) {
                throw new IllegalStateException(push);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!ArrayQueueModule.element().equals(check.peekFirst())) {
                throw new IllegalStateException(dequeue);
            }

            ArrayQueueModule.dequeue();
            check.removeFirst();
        }

        if (!ArrayQueueModule.isEmpty()) {
            throw new IllegalStateException(dequeue);
        }

        System.out.println(ok);
    }

    private static void frontRareTestModule() {
        System.out.print("Front-Rare Test Module: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            ArrayQueueModule.push(a[i]);
            check.addFirst(a[i]);

            if (!ArrayQueueModule.element().equals(check.peekFirst())) {
                throw new IllegalStateException(push);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!ArrayQueueModule.element().equals(check.element())) {
                throw new IllegalStateException(remove);
            }

            ArrayQueueModule.remove();
            check.removeLast();
        }

        if (!ArrayQueueModule.isEmpty()) {
            throw new IllegalStateException(remove);
        }

        System.out.println(ok);
    }

    private static void rareFrontTestModule() {
        System.out.print("Rare-Front Test Module: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            ArrayQueueModule.enqueue(a[i]);
            check.addLast(a[i]);

            if (!ArrayQueueModule.peek().equals(check.peekLast())) {
                throw new IllegalStateException(push);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!ArrayQueueModule.element().equals(check.peekFirst())) {
                throw new IllegalStateException(dequeue);
            }

            ArrayQueueModule.dequeue();
            check.removeFirst();
        }

        if (!ArrayQueueModule.isEmpty()) {
            throw new IllegalStateException(dequeue);
        }

        System.out.println(ok);
    }

    private static void rareRareTestModule() {
        System.out.print("Rare-Rare Test Module: ");

        Integer[] a = generateRandomArray();
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            ArrayQueueModule.enqueue(a[i]);
            check.addLast(a[i]);

            if (!ArrayQueueModule.peek().equals(check.peekLast())) {
                throw new IllegalStateException(enqueue);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!ArrayQueueModule.element().equals(check.peekFirst())) {
                throw new IllegalStateException(remove);
            }

            ArrayQueueModule.remove();
            check.removeLast();
        }

        if (!ArrayQueueModule.isEmpty()) {
            throw new IllegalStateException(remove);
        }

        System.out.println(ok);
    }

    public static Integer[] generateRandomArray(){
        Random random = new Random();
        int n = random.nextInt(100);
        Integer[] array = new Integer[n];

        for (int i = 0; i < n; i++)
        {
            array[i] = random.nextInt(1000);
        }
        return array;
    }

}
