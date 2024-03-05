package expression.exceptions;

import expression.CommonExpression;

public class CheckedGCD extends CheckedOperation {
    public CheckedGCD(CommonExpression first, CommonExpression second) {
        super(first, second);
        this.sign = "gcd";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return 0;
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return 0;
    }
}
