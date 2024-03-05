package expression.exceptions;

import expression.CommonExpression;

public class CheckedDivide extends CheckedOperation {
    public CheckedDivide(CommonExpression first, CommonExpression second) {
        super(first, second);
        this.sign = "/";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int a = first.evaluate(x, y, z);
        int b = second.evaluate(x, y, z);

        if (isDivOverflow(a, b)) {
            throw new OverflowException();
        }
        if (isDBZ(a, b)) {
            throw new DBZException();
        }

        return a / b;
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return isFirstArg ? 4 : 2;
    }

}
