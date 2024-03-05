package expression.generic.operators;

public class CheckedIntegerOperator extends AbstractOperator<Integer> {
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
        return value == Integer.MIN_VALUE ? null : -value;
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
        return value == Integer.MIN_VALUE ? null :  Math.abs(value);
    }

    @Override
    public boolean isAddOverflow(Integer left, Integer right) {
        if (left > 0 && right > 0 && Integer.MAX_VALUE - left < right) {
            return true;
        }
        if (left < 0 && right < 0 && Integer.MIN_VALUE - left > right) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isSubtractOverflow(Integer left, Integer right) {
        if (right == Integer.MIN_VALUE) {
            return left >= 0;
        }
        return isAddOverflow(left, -right);
    }

    @Override
    public boolean isMultiplyOverflow(Integer left, Integer right) {
        if (left == 0 || right == 0) {
            return false;
        } else if (left > 0 && right > 0) {
            return Integer.MAX_VALUE / left < right;
        } else if (left < 0 && right < 0) {
            if (left == Integer.MIN_VALUE || right == Integer.MIN_VALUE ) {
                return true;
            }
            return Integer.MAX_VALUE / (-left) < (-right);
        } else {
            if (left == Integer.MIN_VALUE || right == Integer.MIN_VALUE) {
                if (left == 1 || right == 1) {
                    return false;
                }
                return true;
            }
            if (left < 0) {
                return left != -1 && Integer.MIN_VALUE / left < right;
            } else {
                return right != -1 && Integer.MIN_VALUE / right < left;
            }
        }
    }

    @Override
    public boolean isDivideOverflow(Integer left, Integer right) {
        return left == Integer.MIN_VALUE && right == -1 || right == 0;
    }

    @Override
    public boolean isModOverflow(Integer left, Integer right) {
        return right == 0;
    }
}
