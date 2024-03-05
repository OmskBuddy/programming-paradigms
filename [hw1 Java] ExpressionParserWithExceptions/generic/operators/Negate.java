package expression.generic.operators;

public class Negate<T extends Number> extends UnaryOperator<T> {
    public Negate(TripleExpression<T> value, Operator<T> operator) {
        super(value, operator);
        this.unary = "-";
    }

    @Override
    protected T useUnary(T value) {
        return operator.negate(value);
    }
}
