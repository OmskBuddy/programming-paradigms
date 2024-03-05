package expression.exceptions;

import expression.CommonExpression;
import expression.Variable;

public abstract class CheckedUnary implements CommonExpression {
    protected final CommonExpression value;

    protected String unary;

    public CheckedUnary(CommonExpression value) {
        this.value = value;
    }

    protected abstract int useUnary(int x);

    public boolean isNegOverflow(int x) {
        return x == Integer.MIN_VALUE;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return useUnary(value.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return this.evaluate(x, 0, 0);
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public String toString() {
        return unary + "(" + value.toString() + ")";
    }

    @Override
    public String toMiniString() {
        if (value instanceof CheckedConst || value instanceof Variable || value instanceof CheckedUnary) {
            return unary + " " + value.toMiniString();
        } else {
            return unary + "(" + value.toMiniString() + ")";
        }
    }
}
