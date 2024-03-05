package expression.generic.operators;

public class ShortOperator extends UncheckedOperator<Short> {
    @Override
    protected Short addImpl(Short left, Short right) {
        return (short) (left + right);
    }

    @Override
    protected Short subtractImpl(Short left, Short right) {
        return (short) (left - right);
    }

    @Override
    protected Short multiplyImpl(Short left, Short right) {
        return (short) (left * right);
    }

    @Override
    protected Short divideImpl(Short left, Short right) {
        return (short) (left / right);
    }

    @Override
    protected Short modImpl(Short left, Short right) {
        return (short) (left % right);
    }

    @Override
    public Short negate(Short value) {
        return (short) -value;
    }

    @Override
    public Short getValue(int value) {
        return (short) value;
    }

    @Override
    public Short neutral() {
        return 0;
    }

    @Override
    public Short abs(Short value) {
        return (short) Math.abs(value);
    }

    @Override
    public boolean isDivideOverflow(Short left, Short right) {
        return right.equals(neutral());
    }

    @Override
    public boolean isModOverflow(Short left, Short right) {
        return isDivideOverflow(left, right);
    }
}
