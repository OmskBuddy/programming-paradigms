package expression.generic.operators;

public class Const<T extends Number> implements TripleExpression<T> {

    private final T value;
    private final Operator<T> operator;

    public Const(String s, Operator<T> operator) {
        this.operator = operator;
        this.value = operator.parseToNumber(s);
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return operator.getValue(value);
    }

    @Override
    public int getQueue(boolean isFirstArgument) {
        return 0;
    }
}
