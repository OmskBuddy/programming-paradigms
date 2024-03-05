package expression;

public class Clear extends SetClear {
    public Clear(CommonExpression a, CommonExpression b) {
        super(a, b);
        this.sign = "clear";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int left = a.evaluate(x, y, z);
        int right = b.evaluate(x, y, z);
        left &= ~(1 << right);
        return left;
    }
}
