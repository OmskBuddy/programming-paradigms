package expression.generic.operators;

public class IntegerOperator extends UncheckedOperator<Integer> {
    @Override
    protected Integer addImpl(Integer left, Integer right) {
        return left + right;
    }

    @Override
    protected Integer subtractImpl(Integer left, Integer right) {
        return left - right;
    }

    @Override
    protected Integer multiplyImpl(Integer left, Integer right) {
        return left * right;
    }

    @Override
    protected Integer divideImpl(Integer left, Integer right) {
        return left / right;
    }

    @Override
    protected Integer modImpl(Integer left, Integer right) {
        return left % right;
    }

    @Override
    public Integer negate(Integer value) {
        return -value;
    }

    @Override
    public Integer getValue(int value) {
        return value;
    }

    @Override
    public Integer neutral() {
        return 0;
    }

    @Override
    public Integer abs(Integer value) {
        return Math.abs(value);
    }

    @Override
    public boolean isDivideOverflow(Integer left, Integer right) {
        return right == 0;
    }

    @Override
    public boolean isModOverflow(Integer left, Integer right) {
        return isDivideOverflow(left, right);
    }
}
