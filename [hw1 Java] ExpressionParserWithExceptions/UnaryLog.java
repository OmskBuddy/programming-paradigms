package expression;

public class UnaryLog extends Unary {
    public UnaryLog(CommonExpression value) {
        super(value);
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return 6;
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public int evaluate(int x) {
        return 0;
    }

    @Override
    public String toMiniString() {
        return super.toMiniString();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return super.evaluate(x, y, z);
    }

    @Override
    protected int useUnary(int x) {
        int result = 0;
        while (x >= 10) {
            result++;
            x /= 10;
        }
        return result;
    }
}
