package expression.exceptions;

import expression.CommonExpression;

public class CheckedCount extends CheckedUnary {
    public CheckedCount(CommonExpression value) {
        super(value);
        this.unary = "count";
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return 6;
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    protected int useUnary(int x) {
        return Integer.bitCount(x);
    }
}
