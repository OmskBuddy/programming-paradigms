package expression.exceptions;

import expression.CommonExpression;
import expression.Const;
import expression.Multiply;

public class CheckedReverse extends CheckedUnary {

    public CheckedReverse(CommonExpression elem) {
        super(elem);
        this.unary = "reverse";
    }

    @Override
    protected int useUnary(int x) {
        int result = 0;
        while (x != 0) {
            result = new CheckedAdd(new Multiply(new Const(result), new Const(10)),
                                    new Const(x % 10)).evaluate(0, 0, 0);
            x /= 10;
        }
        return result;
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return 6;
    }
}
