package expression;

public abstract class CommonMultDiv extends BinaryOperation {

    public CommonMultDiv(CommonExpression a, CommonExpression b) {
        super(a, b);
    }

    @Override
    public String toMiniString() {
        String left = a.toMiniString();
        String right = b.toMiniString();

        StringBuilder result = new StringBuilder();

        result.append(left).append(" ").append(sign).append(" ");
        if (b.getQueue(false) == this.getQueue(true) && b.getClass() != this.getClass()) {
            result.append("(").append(right).append(")");
        } else {
            result.append(right);
        }

        return result.toString();
    }
}
