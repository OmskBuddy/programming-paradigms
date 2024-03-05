package expression.generic.operators;

public class Abs<T extends Number> extends UnaryOperator<T> {
    public Abs(TripleExpression<T> value, Operator<T> operator) {
        super(value, operator);
        this.unary = "abs";
    }

    @Override
    protected T useUnary(T value) {
        return operator.abs(value);
    }
}
