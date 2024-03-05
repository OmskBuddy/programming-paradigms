package expression.generic.operators;

import expression.ToMiniString;

public interface TripleExpression<T extends Number> extends ToMiniString {
    T evaluate(int x, int y, int z);
    int getQueue(boolean isFirstArgument);
}
