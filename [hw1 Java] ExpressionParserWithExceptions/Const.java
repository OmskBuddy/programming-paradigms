package expression;

public class Const implements CommonExpression {

    private final Number value;

    public Const(int value) {
        this.value = value;
    }

    public Const(double value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return value.intValue();
    }

    @Override
    public double evaluate(double x) {
        return value.doubleValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value.intValue();
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public String toString() {
        if (value instanceof Double) {
            return Double.toString(value.doubleValue());
        } else if (value instanceof Integer) {
            return Integer.toString(value.intValue());
        } else if (value instanceof Long) {
            return Long.toString(value.longValue());
        }
        return null;
    }

    @Override
    public boolean equals(Object that) {
        return that != null &&
                this.getClass() == that.getClass() &&
                this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return 5;
    }
}