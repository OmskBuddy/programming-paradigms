package expression;

public class UnaryCount extends Unary {
    public UnaryCount(CommonExpression value) {
        super(value);
        this.unary = "count";
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
    protected int useUnary(int x) {
        return Integer.bitCount(x);
    }
}
