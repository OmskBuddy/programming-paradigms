package expression;

public class Lcm extends CommonMultDiv {
    public Lcm(CommonExpression a, CommonExpression b) {
        super(a, b);
        this.sign = "lcm";
    }

    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return (int)((long)a * b / Gcd.gcd(Math.abs((long)a), Math.abs((long)b)));
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
        return lcm(a.evaluate(x, y, z), b.evaluate(x, y, z));
    }
}
