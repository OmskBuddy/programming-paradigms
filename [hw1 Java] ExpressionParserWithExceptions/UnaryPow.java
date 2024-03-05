package expression;

public class UnaryPow extends Unary {
    public UnaryPow(CommonExpression value) {
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
        return 0;
    }

    @Override
    protected int useUnary(int x) {
        int result = 1;
        for (int i = 0; i < x; i++) {
            result *= 10;
        }
        return result;
    }
}
