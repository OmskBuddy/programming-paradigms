package expression.generic.operators;

public class Mod<T extends Number> extends BinaryOperation<T> {
    public Mod(TripleExpression<T> left, TripleExpression<T> right, Operator<T> operator) {
        super(left, right, operator);
        this.sign = "mod";
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return operator.mod(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public int getQueue(boolean isFirstArgument) {
        return 0;
    }
}
