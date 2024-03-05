package expression.generic.operators;

public class Square<T extends Number> extends UnaryOperator<T> {
    public Square(TripleExpression<T> value, Operator<T> operator) {
        super(value, operator);
        this.unary = "square";
    }

    @Override
    protected T useUnary(T value) {
        return operator.multiply(value, value);
    }
}
