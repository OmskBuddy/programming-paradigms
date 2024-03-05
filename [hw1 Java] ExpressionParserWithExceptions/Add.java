package expression;

public class Add extends BinaryOperation {

    public Add(CommonExpression a, CommonExpression b) {
        super(a, b);
        this.sign = "+";
    }

    @Override
    public double evaluate(double x) {
        return a.evaluate(x) + b.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return a.evaluate(x, y, z) + b.evaluate(x, y, z);
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return 0;
    }
}
