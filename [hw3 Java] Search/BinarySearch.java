package search;

import java.util.Arrays;

public class BinarySearch {

    public static void main(String[] args) {
        int n = args.length - 1;
        int[] a = new int[n + 1];

        int x = Integer.parseInt(args[0]);
        int sum = 0;

        for (int i = 1; i <= n; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
            sum += a[i - 1];
        }
        a[n] = Integer.MIN_VALUE;

        // Pred: a != null && a is sorted non-ascending
        int result = sum % 2 == 1 ?
                BinarySearch.iterativeLowerBound(a, x) : BinarySearch.recursiveLowerBound(a, x);
        // Post: a.length == 0 && result == 0 || a[result] <= x && a[result - 1] > x && result >= 0

        System.out.println(result);
    }

    // Model: a[-1] = inf && a.back() = MIN_VALUE
    // Pred: a != null && a is sorted non-ascending
    // Post: a.length == 0 && R == 0 || a[R] <= x && a[R - 1] > x && R >= 0
    public static int iterativeLowerBound(int[] a, int x) {
        if (a.length == 0) {
            // a.length == 0 && R == 0
            return 0;
        }

        int left = -1;
        int right = a.length - 1;
        // a != null && a.length > 0 && left == -1 && right == a.length - 1

        // I: such r: a[r] <= x && a[r - 1] > x is in (left', right']
        while (right - left > 1) {
            // left' < r <= right' && right' - left' > 1
            int m = (left + right) / 2;
            // left' < r <= right' && left' < m <= right' && right' - left' > 1
            if (a[m] <= x) {
                // left' < r <= right' && left' < m <= right' && right' - left' > 1 && a[m] <= x
                // r <= m
                right = m;
                // left' < r <= right'
            } else {
                // left' < r <= right' && left' < m <= right' && right' - left' > 1 && a[m] > x
                // r > m
                left = m;
                // left' < r <= right'
            }
            // left' < r <= right'
        }
        // left' < r <= right' && right' - left' == 1 -> r == right'
        // a[right'] <= x && a[right' - 1] > x
        return right;
    }

    // Model: a[-1] = inf && a.back() = MIN_VALUE
    // Pred: a != null && a is sorted non-ascending
    // Post: a.length == 0 && R == 0 || a[R] <= x && a[R - 1] > x && R >= 0
    public static int recursiveLowerBound(int[] a, int x) {
        if (a.length <= 1) {
            // a.length == 0 && R == 0 || a.length == 1 && R == 0 && a.back() = a[R] = MIN_VALUE <= x && a[R - 1] = inf > x
            return 0;
        }

        int left = -1;
        int right = a.length - 1;
        int m = (left + right) / 2;
        // a != null && left == -1 && right == a.length && left < m <= right
        // such r: a[r] <= x && a[r - 1] > x is in (left', right']
        if (a[m] <= x) {
            // left' < r <= right' && a[m] <= x
            // r <= m
            right = m;
            // left' < r <= right'
        } else {
            // left' < r <= right' && a[m] > x
            // r > m
            left = m;
            // left' < r <= right'
        }
        // left' < r <= right'

        // recursiveLowerBound(a(left', right'], x) will return R': a(left', right'].length == 0 && R' == 0 || a(left', right'][R'] <= x && a(left', right'][R' - 1] > x && R' >= 0
        // R = left + 1 + recursiveLowerBound(a(left', right'], x) = left + 1 + R'
        // a[R] <= x && a[R - 1] > x && R >= 0
        return left + 1 + recursiveLowerBound(Arrays.copyOfRange(a, left + 1, right + 1), x); // as right > left, a(left, right] != null && as a(left, right] is sub-segment of a, a(left, right] is sorted non-ascending
    }

}
