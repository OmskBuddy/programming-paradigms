package expression.generic.operators;

import java.math.BigInteger;

public class BigIntegerOperator extends UncheckedOperator<BigInteger> {
    @Override
    protected BigInteger addImpl(BigInteger left, BigInteger right) {
        return left.add(right);
    }

    @Override
    protected BigInteger subtractImpl(BigInteger left, BigInteger right) {
        return left.subtract(right);
    }

    @Override
    protected BigInteger multiplyImpl(BigInteger left, BigInteger right) {
        return left.multiply(right);
    }

    @Override
    protected BigInteger divideImpl(BigInteger left, BigInteger right) {
        return left.divide(right);
    }

    @Override
    protected BigInteger modImpl(BigInteger left, BigInteger right) {
        return left.mod(right);
    }

    @Override
    public BigInteger negate(BigInteger value) {
        return value.negate();
    }

    @Override
    public BigInteger getValue(int value) {
        return BigInteger.valueOf(value);
    }

    @Override
    public BigInteger neutral() {
        return BigInteger.valueOf(0);
    }

    @Override
    public BigInteger abs(BigInteger value) {
        return value.abs();
    }

    @Override
    public boolean isDivideOverflow(BigInteger left, BigInteger right) {
        return getValue(right).equals(neutral());
    }

    @Override
    public boolean isModOverflow(BigInteger left, BigInteger right) {
        return right.compareTo(neutral()) <= 0;
    }
}
