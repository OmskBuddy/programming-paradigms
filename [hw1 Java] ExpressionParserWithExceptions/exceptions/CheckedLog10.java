package expression.exceptions;

import expression.CommonExpression;

public class CheckedLog10 extends CheckedPowLog {
    private final int base = 10;
    public CheckedLog10(CommonExpression value) {
        super(value);
        this.unary = "log10";
    }

    @Override
    protected int useUnary(int x) {
        if (isLogOverflow(x)) {
            throw new OverflowException();
        }

        int result = 0;
        int mult = 1;
        while (mult * base <= x) {
            if (CheckedOperation.isMultOverflow(mult, base)) {
                break;
            }
            result++;
            mult *= base;
        }
        return result;
    }
}
