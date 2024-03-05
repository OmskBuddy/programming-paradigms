package queue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

import static queue.ArrayQueueADT.*;

public class QueueADTTest {

    static String ok = "OK";
    static String pushTest = "Your Queue implement method push() incorrect!";
    static String removeTest = "Your Queue implement method remove() incorrect!";
    static String dequeueTest = "Your Queue implement method dequeue() incorrect!";
    static String enqueueTest = "Your Queue implement method enqueue() incorrect!";
    static String toArrayTest = "Your Queue implement method toArray() incorrect!";
    static String clearTest = "Your Queue implement method clear() incorrect!";

    public static void main(String[] args) {
        ArrayQueueADT queue = new ArrayQueueADT();
        arrayQueueADTTest(queue);
    }

    public static void arrayQueueADTTest(ArrayQueueADT queue) {
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

    private static void nullTestModule(ArrayQueueADT queue) {
        System.out.print("NullTest ADT: ");
        try {
            enqueue(queue, null);
            throw new NullPointerException("Your Queue doesnt' check null in output!");
        } catch (NullPointerException e) {
            System.out.println(ok);
        }
    }

    private static void differentTypeTestModule(ArrayQueueADT queue) {
        System.out.println("DifferentTypeTest ADT: ");
        except(queue, "\tstring: ", "Hallo");
        except(queue, "\tarray: ", generateRandomArray(queue));
        except(queue, "\tdouble: ", new Random().nextDouble());
    }

    private static void except(ArrayQueueADT queue, String s, Object elem) {
        System.out.print(s);

        push(queue, elem);
        Object out = dequeue(queue);

        if (!out.equals(elem)) {
            throw new IllegalStateException("Incorrect type processing!");
        } else {
            System.out.println(ok);
        }
    }

    private static void emptyTestModule(ArrayQueueADT queue) {
        System.out.println("EmptyTest ADT: ");
        try {
            System.out.print("\tfront check: ");
            dequeue(queue);
            throw new NoSuchElementException("Your Queue try delete first element from empty queue!");
        } catch (NoSuchElementException e) {
            System.out.println(ok);
        }

        try {
            System.out.print("\trare check: ");
            remove(queue);
            throw new NoSuchElementException("Your Queue try delete last element from empty queue!");
        } catch (NoSuchElementException e) {
            System.out.println(ok);
        }
    }

    private static void frontClearTestModule(ArrayQueueADT queue) {
        System.out.print("Front-Clear Test ADT: ");

        Integer[] a = generateRandomArray(queue);
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            push(queue, a[i]);
            check.addFirst(a[i]);

            if (!element(queue).equals(check.peekFirst())) {
                throw new IllegalStateException(pushTest);
            }
        }

        clear(queue);
        check.clear();

        if (!Arrays.equals(toArray(queue), check.toArray())) {
            throw new IllegalStateException(clearTest);
        }

        System.out.println(ok);
    }

    private static void rareClearTestModule(ArrayQueueADT queue) {
        System.out.print("Rare-Clear Test ADT: ");

        Integer[] a = generateRandomArray(queue);
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            enqueue(queue, a[i]);
            check.addLast(a[i]);

            if (!peek(queue).equals(check.peekLast())) {
                throw new IllegalStateException(enqueueTest);
            }
        }

        clear(queue);
        check.clear();

        if (!Arrays.equals(toArray(queue), check.toArray())) {
            throw new IllegalStateException(clearTest);
        }
        System.out.println(ok);
    }

    private static void toArrayTestModule(ArrayQueueADT queue) {
        System.out.print("toArrayText ADT: ");

        Integer[] a = generateRandomArray(queue);
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            enqueue(queue, a[i]);
            check.add(a[i]);
        }

        if (Arrays.equals(toArray(queue), check.toArray())) {
            System.out.println(ok);
            clear(queue);
        } else {
            throw new IllegalStateException(toArrayTest);
        }
    }

    private static void frontFrontTestModule(ArrayQueueADT queue) {
        System.out.print("Front-Front Test ADT: ");

        Integer[] a = generateRandomArray(queue);
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            push(queue, a[i]);
            check.addFirst(a[i]);

            if (!element(queue).equals(check.peekFirst())) {
                throw new IllegalStateException(pushTest);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!element(queue).equals(check.peekFirst())) {
                throw new IllegalStateException(dequeueTest);
            }

            dequeue(queue);
            check.removeFirst();
        }

        if (!isEmpty(queue)) {
            throw new IllegalStateException(dequeueTest);
        }

        System.out.println(ok);
    }

    private static void frontRareTestModule(ArrayQueueADT queue) {
        System.out.print("Front-Rare Test ADT: ");

        Integer[] a = generateRandomArray(queue);
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            push(queue, a[i]);
            check.addFirst(a[i]);

            if (!element(queue).equals(check.peekFirst())) {
                throw new IllegalStateException(pushTest);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!element(queue).equals(check.element())) {
                throw new IllegalStateException(removeTest);
            }

            remove(queue);
            check.removeLast();
        }

        if (!isEmpty(queue)) {
            throw new IllegalStateException(removeTest);
        }

        System.out.println(ok);
    }

    private static void rareFrontTestModule(ArrayQueueADT queue) {
        System.out.print("Rare-Front Test ADT: ");

        Integer[] a = generateRandomArray(queue);
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            enqueue(queue, a[i]);
            check.addLast(a[i]);

            if (!peek(queue).equals(check.peekLast())) {
                throw new IllegalStateException(pushTest);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!element(queue).equals(check.peekFirst())) {
                throw new IllegalStateException(dequeueTest);
            }

            dequeue(queue);
            check.removeFirst();
        }

        if (!isEmpty(queue)) {
            throw new IllegalStateException(dequeueTest);
        }

        System.out.println(ok);
    }

    private static void rareRareTestModule(ArrayQueueADT queue) {
        System.out.print("Rare-Rare Test ADT: ");

        Integer[] a = generateRandomArray(queue);
        int n = a.length;
        ArrayDeque<Object> check = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            enqueue(queue, a[i]);
            check.addLast(a[i]);

            if (!peek(queue).equals(check.peekLast())) {
                throw new IllegalStateException(enqueueTest);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!element(queue).equals(check.peekFirst())) {
                throw new IllegalStateException(removeTest);
            }

            remove(queue);
            check.removeLast();
        }

        if (!isEmpty(queue)) {
            throw new IllegalStateException(removeTest);
        }

        System.out.println(ok);
    }

    public static Integer[] generateRandomArray(ArrayQueueADT queue){
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
