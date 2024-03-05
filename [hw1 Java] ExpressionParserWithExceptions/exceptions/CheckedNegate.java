package expression.exceptions;

import expression.CommonExpression;

public class CheckedNegate extends CheckedUnary {
    public CheckedNegate(CommonExpression value) {
        super(value);
        unary = "-";
    }

    @Override
    protected int useUnary(int x) {
        if (isNegOverflow(x)) {
            throw new OverflowException();
        }
        return -x;
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return 6;
    }
}
