package expression.exceptions;

import expression.CommonExpression;

public class CheckedLCM extends CheckedOperation {
    public CheckedLCM(CommonExpression first, CommonExpression second) {
        super(first, second);
        this.sign = "lcm";
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
