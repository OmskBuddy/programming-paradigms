package expression;

public class Gcd extends CommonMultDiv {
    public Gcd(CommonExpression a, CommonExpression b) {
        super(a, b);
        this.sign = "gcd";
    }

    public static long gcd(long a, long b) {
        if (a == 0 || b == 0) {
            return a + b;
        }
        return gcd(b, a % b);
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
    public int evaluate(int x, int y, int z) {
        return (int)Gcd.gcd(Math.abs((long)a.evaluate(x, y, z)), Math.abs((long)b.evaluate(x, y, z)));
    }
}
