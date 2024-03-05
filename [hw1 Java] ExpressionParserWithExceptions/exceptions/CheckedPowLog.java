package expression.exceptions;

import expression.CommonExpression;

public abstract class CheckedPowLog extends CheckedUnary {
    public CheckedPowLog(CommonExpression value) {
        super(value);
    }

    public static boolean isPowOverflow(int x) {
        return x < 0;
    }

    public static boolean isLogOverflow(int x) {
        return x <= 0;
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return 6;
    }
}
