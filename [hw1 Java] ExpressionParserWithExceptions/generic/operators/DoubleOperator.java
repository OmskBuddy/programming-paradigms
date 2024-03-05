package expression.generic.operators;

public class DoubleOperator extends UncheckedOperator<Double> {
    @Override
    protected Double addImpl(Double left, Double right) {
        return left + right;
    }

    @Override
    protected Double subtractImpl(Double left, Double right) {
        return left - right;
    }

    @Override
    protected Double multiplyImpl(Double left, Double right) {
        return left * right;
    }

    @Override
    protected Double divideImpl(Double left, Double right) {
        return left / right;
    }

    @Override
    protected Double modImpl(Double left, Double right) {
        return left % right;
    }

    @Override
    public Double negate(Double value) {
        return -value;
    }

    @Override
    public Double getValue(int value) {
        return (double) value;
    }

    @Override
    public Double neutral() {
        return (double) 0;
    }

    @Override
    public Double abs(Double value) {
        return Math.abs(value);
    }

    @Override
    public boolean isDivideOverflow(Double left, Double right) {
        return false;
    }

    @Override
    public boolean isModOverflow(Double left, Double right) {
        return isDivideOverflow(left, right);
    }
}
