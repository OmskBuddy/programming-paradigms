package expression.generic.operators;

public abstract class BinaryOperation<T extends Number> implements TripleExpression<T> {

    protected final TripleExpression<T> left;
    protected final TripleExpression<T> right;
    protected final Operator<T> operator;
    protected String sign;

    public BinaryOperation(TripleExpression<T> left, TripleExpression<T> right, Operator<T> operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public String toMiniString() {
        String newA = left.toMiniString();
        String newB = right.toMiniString();
        StringBuilder result = new StringBuilder();

        if (left.getQueue(true) < this.getQueue(false)) {
            result.append("(").append(newA).append(")");
        } else {
            result.append(newA);
        }

        result.append(" ").append(sign).append(" ");

        if (right.getQueue(false) < this.getQueue(true)) {
            result.append("(").append(newB).append(")");
        } else {
            result.append(newB);
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + sign + " " + right.toString() + ")";
    }
}
