package expression.exceptions;

import expression.CommonExpression;

public class CheckedSubtract extends CheckedOperation {
    public CheckedSubtract(CommonExpression first, CommonExpression second) {
        super(first, second);
        this.sign = "-";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int a = first.evaluate(x, y, z);
        int b = second.evaluate(x, y, z);

        if (isDiffOverflow(a, b)) {
            throw new OverflowException();
        }

        return a - b;
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return isFirstArg ? 1 : 0;
    }
}
