package expression.generic.operators;

import java.util.Arrays;

public class Variable<T extends Number> implements TripleExpression<T> {

    private final String[] names = {"x", "y", "z"};
    private final String var;
    private final Operator<T> operator;

    public Variable(char var, Operator<T> operator) {
        String inputVar = Character.toString(var);
        if (!Arrays.asList(names).contains(inputVar)) {
            throw new RuntimeException("Incorrect name of variable " + inputVar);
        }
        this.var = inputVar;
        this.operator = operator;
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
    public T evaluate(int x, int y, int z) {
        if (var.equals(names[0])) {
            return operator.getValue(x);
        } else if (var.equals(names[1])) {
            return operator.getValue(y);
        } else {
            return operator.getValue(z);
        }
    }

    @Override
    public int getQueue(boolean isFirstArgument) {
        return 0;
    }
}
