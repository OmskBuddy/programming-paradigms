package search;

import java.io.DataInput;
import java.util.Arrays;

public class BinarySearchUni {

    public static void main(String[] args) {
        int n = args.length;
        int[] a = new int[n + 1];
        int sum = 0;

        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(args[i]);
            sum += a[i];
        }
        a[n] = Integer.MIN_VALUE;
        // Pred: a != null && a - concatenation of strictly increasing and strictly decreasing array
        int result = sum % 2 == 1 ? BinarySearchUni.iterativeSearchUni(a) :
                BinarySearchUni.recursiveSearchUni(a);
        // Post: R - size of min possible strictly increasing array: a[R] >= a[R - 1] && (a[R] - last element || a[R] >= a[R + 1])

        System.out.println(result);
    }

    // Model: a[-1] = MIN_VALUE && a.back() = MIN_VALUE
    // Pred: a != null && a - concatenation of strictly increasing and strictly decreasing array
    // Post: R - size of min possible strictly increasing array: a[R] >= a[R - 1] && (a[R] - last element || a[R] >= a[R + 1])
    public static int iterativeSearchUni(int[] a) {
        if (a.length <= 2) {
            // R = 0: a[R] >= a[R - 1] && a[R] - last element (in condition of we have one MIN_VALUE added in the end of array)
            return 0;
        }

        int left = -1;
        int right = a.length;
        // left == -1 && right == a.length && a != null && a - concatenation of strictly increasing and strictly decreasing array

        // I: such r: [ a[r] >= a[r - 1] && (a[r] - last elem || a[r] >= a[r + 1]) ] is in (left, right]
        while (right - left > 1) {
            // left' < r <= right && right - left > 1
            int m = (left + right) / 2;
            // left' < r <= right' && left' < m <= right' && right' - left' > 1
            if (a[m] > a[m + 1]) {
                // left' < r <= right' && left' < m <= right' && right' - left' > 1 && a[m] > a[m + 1]
                // r <= m
                right = m;
                // left' < r <= right'
            } else {
                // left' < r <= right' && left' < m <= right' && right' - left' > 1 && a[m] <= a[m + 1]
                // r > m
                left = m;
                // left' < r <= right'
            }
            // left' < r <= right'
        }
        // left' < r <= right' && right' - left' == 1 -> r == right'
        // a[r] >= a[r - 1] && (a[r] - last elem || a[r] >= a[r + 1])
        return right;
    }

    // Model: a[-1] = MIN_VALUE && a.back() = MIN_VALUE
    // Pred: a != null && a - concatenation of strictly increasing and strictly decreasing array
    // Post: R - size of min possible strictly increasing array: a[R] >= a[R - 1] && (a[R] - last element || a[R] >= a[R + 1])
    public static int recursiveSearchUni(int[] a) {
        if (a.length <= 1) {
            // R = 0: a[R] >= a[R - 1] && a[R] - last element (condition of having added element in the end of array can be false)
            return 0;
        }

        int left = -1;
        int right = a.length - 1;
        int m = (left + right) / 2;
        // left == -1 && right == a.length - 1 && a != null && a - concatenation of strictly increasing and strictly decreasing array
        // such r: [ a[r] >= a[r - 1] && (a[r] - last elem || a[r] >= a[r + 1]) ] is in (left, right]
        if (a[m] > a[m + 1]) {
            // left' < r <= right' && a[m] > a[m + 1]
            // r <= m
            right = m;
            // left' < r <= right'
        } else {
            // left' < r <= right' && a[m] <= a[m + 1]
            // r > left'
            left = m;
            // left' < r <= right'
        }
        // left' < r <= right'

        // recursiveSearchUni(a') will return R': a'[R'] >= a'[R' - 1] && (a'[R'] - last element of a' || a'[R] >= a'[R + 1])
        // R = left + 1 + recursiveLowerBound(a(left', right'], x) = left + 1 + R'
        // a[R] >= a[R - 1] && (a[R] - last element || a[R] >= a[R + 1])
        return left + 1 + recursiveSearchUni(Arrays.copyOfRange(a, left + 1, right + 1));
    }

}
