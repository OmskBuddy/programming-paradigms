package expression;

public class Set extends SetClear {
    public Set(CommonExpression a, CommonExpression b) {
        super(a, b);
        this.sign = "set";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int first = a.evaluate(x, y, z);
        int second = b.evaluate(x, y, z);
        first |= (1 << second);
        return first;
    }
}
