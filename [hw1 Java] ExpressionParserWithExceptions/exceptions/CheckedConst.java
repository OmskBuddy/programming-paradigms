package expression.exceptions;

import expression.CommonExpression;
import expression.Const;

public class CheckedConst implements CommonExpression {

    private int value;

    public CheckedConst(String value) {
        this.value = parseToInt(value);
    }

    private int parseToInt(String value) {
        int number = 0;
        if (value.charAt(0) == '-') {
            for (int i = 1; i < value.length(); i++) {
                number = new CheckedMultiply(new Const(number), new Const(10)).evaluate(0, 0, 0);
                number = new CheckedSubtract(new Const(number), new Const(Character.getNumericValue(value.charAt(i)))).evaluate(0, 0, 0);
            }
        } else {
            for (int i = 0; i < value.length(); i++) {
                number = new CheckedMultiply(new Const(number), new Const(10)).evaluate(0, 0, 0);
                number = new CheckedAdd(new Const(number), new Const(Character.getNumericValue(value.charAt(i)))).evaluate(0, 0, 0);
            }
        }
        return number;
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return 5;
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }
}
