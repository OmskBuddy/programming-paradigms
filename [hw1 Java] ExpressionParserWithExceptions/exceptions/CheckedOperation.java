package expression.exceptions;

import expression.CommonExpression;

public abstract class CheckedOperation implements CommonExpression {
    protected CommonExpression first;
    protected CommonExpression second;
    protected String sign;
    public CheckedOperation(CommonExpression first, CommonExpression second) {
        this.first = first;
        this.second = second;
    }
    public static boolean isSumOverflow(int left, int right) {
        if (left > 0 && right > 0 && Integer.MAX_VALUE - left < right) {
            return true;
        }
        if (left < 0 && right < 0 && Integer.MIN_VALUE - left > right) {
            return true;
        }
        return false;
    }

    public static boolean isDiffOverflow(int left, int right) {
        if (right == Integer.MIN_VALUE) {
            return left >= 0;
        }
        return isSumOverflow(left, -right);
    }

    public static boolean isMultOverflow(int left, int right) {
        if (left == 0 || right == 0) {
            return false;
        } else if (left > 0 && right > 0) {
            return Integer.MAX_VALUE / left < right;
        } else if (left < 0 && right < 0) {
            if (left == Integer.MIN_VALUE || right == Integer.MIN_VALUE ) {
                return true;
            }
            return Integer.MAX_VALUE / (-left) < (-right);
        } else {
            if (left == Integer.MIN_VALUE || right == Integer.MIN_VALUE) {
                if (left == 1 || right == 1) {
                    return false;
                }
                return true;
            }
            if (left < 0) {
                return left != -1 && Integer.MIN_VALUE / left < right;
            } else {
                return right != -1 && Integer.MIN_VALUE / right < left;
            }
        }
    }

    public static boolean isDivOverflow(int left, int right) {
        return left == Integer.MIN_VALUE && right == -1;
    }

    public static boolean isDBZ(int left, int right) {
        return right == 0;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + " " + sign + " " + second.toString() + ")";
    }

    @Override
    public String toMiniString() {
        String newFirst = first.toMiniString();
        String newSecond = second.toMiniString();
        StringBuilder result = new StringBuilder();

        if (first.getQueue(true) < this.getQueue(false)) {
            result.append("(").append(newFirst).append(")");
        } else {
            result.append(newFirst);
        }

        result.append(" ").append(sign).append(" ");

        if (second.getQueue(false) < this.getQueue(true)) {
            result.append("(").append(newSecond).append(")");
        } else {
            result.append(newSecond);
        }
        return result.toString();
    };

    @Override
    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }
}
