package expression;

public class UnaryReverse extends Unary {

    private static String unary = "reverse";
    public UnaryReverse(CommonExpression value) {
        super(value);
    }

    @Override
    protected int useUnary(int x) {
        int result = 0;
        while (x != 0) {
            result = result * 10 + x % 10;
            x /= 10;
        }
        return result;
    }

    @Override
    public String toMiniString() {
        return super.toMiniString();
    }

    @Override
    public String toString() {
        return unary + "(" + value.toString() + ")";
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
