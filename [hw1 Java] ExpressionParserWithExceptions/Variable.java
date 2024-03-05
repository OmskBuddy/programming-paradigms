package expression;

import java.util.Arrays;

public class Variable implements CommonExpression {

    private final String[] names = {"x", "y", "z"};

    private final String var;

    public Variable(String var) {
        if (!Arrays.asList(names).contains(var)) {
            throw new RuntimeException(var);
        }
        this.var = var;
    }

    public Variable(char var) {
        String inputVar = Character.toString(var);
        if (!Arrays.asList(names).contains(inputVar)) {
            throw new RuntimeException("Incorrect name of variable " + inputVar);
        }
        this.var = inputVar;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (var.equals(names[0])) {
            return x;
        } else if (var.equals(names[1])) {
            return y;
        } else {
            return z;
        }
    }

    @Override
    public boolean equals(Object that) {
        return that != null &&
                this.getClass() == that.getClass() &&
                this.hashCode() == ((Variable)that).hashCode();
    }

    @Override
    public int hashCode() {
        return var.hashCode();
    }

    @Override
    public int getQueue(boolean isFirstArg) {
        return 5;
    }
}
