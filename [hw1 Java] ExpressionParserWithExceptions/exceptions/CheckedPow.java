package expression.exceptions;

import expression.CommonExpression;
import expression.Const;

public class CheckedPow extends CheckedPowLog {
    public CheckedPow(CommonExpression value) {
        super(value);
        this.unary = "pow10";
    }

    @Override
    protected int useUnary(int x) {
        if (isPowOverflow(x)) {
            throw new OverflowException();
        }

        int result = 1;
        for (int i = 0; i < x; i++) {
            result = new CheckedMultiply(new Const(result), new Const(10)).evaluate(0, 0, 0);
        }
        return result;
    }
}
