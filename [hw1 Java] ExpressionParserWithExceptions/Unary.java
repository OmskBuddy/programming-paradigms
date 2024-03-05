package expression;

public abstract class Unary implements CommonExpression {

    protected final CommonExpression value;
    protected String unary;
    public Unary(CommonExpression value) {
        this.value = value;
    }

    protected abstract int useUnary(int x);

    @Override
    public int evaluate(int x, int y, int z) {
        return useUnary(value.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }

    @Override
    public String toMiniString() {
        if (value instanceof Const || value instanceof Variable || value instanceof Unary) {
            return unary + " " + value.toMiniString();
        } else {
            return unary + "(" + value.toMiniString() + ")";
        }
    }

    @Override
    public String toString() {
        return unary + "(" + value.toString() + ")";
    }
}
