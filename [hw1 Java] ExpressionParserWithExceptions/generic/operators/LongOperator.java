package expression.generic.operators;

public class LongOperator extends UncheckedOperator<Long> {
    @Override
    protected Long addImpl(Long left, Long right) {
        return left + right;
    }

    @Override
    protected Long subtractImpl(Long left, Long right) {
        return left - right;
    }

    @Override
    protected Long multiplyImpl(Long left, Long right) {
        return left * right;
    }

    @Override
    protected Long divideImpl(Long left, Long right) {
        return left / right;
    }

    @Override
    protected Long modImpl(Long left, Long right) {
        return left % right;
    }

    @Override
    public Long negate(Long value) {
        return -value;
    }

    @Override
    public Long getValue(int value) {
        return (long) value;
    }

    @Override
    public Long neutral() {
        return 0L;
    }

    @Override
    public Long abs(Long value) {
        return Math.abs(value);
    }

    @Override
    public boolean isDivideOverflow(Long left, Long right) {
        return right == 0;
    }

    @Override
    public boolean isModOverflow(Long left, Long right) {
        return isDivideOverflow(left, right);
    }
}
