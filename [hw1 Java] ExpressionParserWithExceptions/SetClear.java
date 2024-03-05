package expression;

public abstract class SetClear extends BinaryOperation {
    public SetClear(CommonExpression a, CommonExpression b) {
        super(a, b);
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return -1;
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public String toMiniString() {
        String left = a.toMiniString();
        String right = b.toMiniString();

        StringBuilder result = new StringBuilder();

        result.append(left).append(" ").append(sign).append(" ");
        if (b.getQueue(false) == this.getQueue(true)) {
            result.append("(").append(right).append(")");
        } else {
            result.append(right);
        }

        return result.toString();
    }
}
