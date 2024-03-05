package expression;

public class UnaryMinus extends Unary {
    public UnaryMinus(CommonExpression value) {
        super(value);
        this.unary = "-";
    }

    @Override
    protected int useUnary(int x) {
        return -x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return super.evaluate(x, y, z);
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
}
