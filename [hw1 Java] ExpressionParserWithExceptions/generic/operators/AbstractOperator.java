package expression.generic.operators;

import java.math.BigInteger;

public abstract class AbstractOperator<T extends Number> implements Operator<T> {
    @Override
    public T getValue(T value) {
        return value;
    }

    @Override
    public T add(T left, T right) {
        if (left == null || right == null || isAddOverflow(left, right)) {
            return null;
        };
        return addImpl(left, right);
    }

    protected abstract T addImpl(T left, T right);

    @Override
    public T subtract(T left, T right) {
        if (left == null || right == null || isSubtractOverflow(left, right)) {
            return null;
        }
        return subtractImpl(left, right);
    }

    protected abstract T subtractImpl(T left, T right);

    @Override
    public T multiply(T left, T right) {
        if (left == null || right == null || isMultiplyOverflow(left, right)) {
            return null;
        }
        return multiplyImpl(left, right);
    }

    protected abstract T multiplyImpl(T left, T right);

    @Override
    public T divide(T left, T right) {
        if (left == null || right == null || isDivideOverflow(left, right)) {
            return null;
        }
        return divideImpl(left, right);
    }

    protected abstract T divideImpl(T left, T right);

    @Override
    public T mod(T left, T right) {
        if (left == null || right == null || isModOverflow(left, right)) {
            return null;
        }
        return modImpl(left, right);
    }

    protected abstract T modImpl(T left, T right);

    @Override
    public T parseToNumber(String s) {
        T result = neutral();
        if (s.charAt(0) == '-') {
            for (int i = 1; i < s.length(); i++) {
                result = this.multiply(result, getValue(10));
                result = this.subtract(result, getValue(Character.getNumericValue(s.charAt(i))));
            }
        } else {
            for (int i = 0; i < s.length(); i++) {
                result = this.multiply(result, getValue(10));
                result = this.add(result, getValue(Character.getNumericValue(s.charAt(i))));
            }
        }
        return result;
    }
}
