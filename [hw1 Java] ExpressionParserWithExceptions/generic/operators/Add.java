package expression.generic.operators;

public class Add<T extends Number> extends BinaryOperation<T> {
    public Add(TripleExpression<T> left, TripleExpression<T> right, Operator<T> operator) {
        super(left, right, operator);
        this.sign = "+";
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return operator.add(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public int getQueue(boolean isFirstArgument) {
        return 0;
    }
}
