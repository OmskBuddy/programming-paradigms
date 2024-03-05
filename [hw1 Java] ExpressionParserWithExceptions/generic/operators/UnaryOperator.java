package expression.generic.operators;

public abstract class UnaryOperator<T extends Number> implements TripleExpression<T>  {

    protected final TripleExpression<T> value;
    protected final Operator<T> operator;
    protected String unary;

    public UnaryOperator(TripleExpression<T> value, Operator<T> operator) {
        this.value = value;
        this.operator = operator;
    }

    @Override
    public String toMiniString() {
        if (value instanceof BinaryOperation) {
            return unary + "(" + value.toMiniString() + ")";
        } else {
            return unary + " " + value.toMiniString();
        }
    }

    @Override
    public T evaluate(int x, int y, int z) {
        T result = value.evaluate(x, y, z);
        return result != null ? useUnary(result) : null;
    }

    protected abstract T useUnary(T value);

    @Override
    public int getQueue(boolean isFirstArgument) {
        return 0;
    }
}
