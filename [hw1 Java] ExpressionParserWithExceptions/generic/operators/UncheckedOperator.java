package expression.generic.operators;

public abstract class UncheckedOperator<T extends Number> extends AbstractOperator<T> {
    @Override
    public boolean isAddOverflow(T left, T right) {
        return false;
    }

    @Override
    public boolean isSubtractOverflow(T left, T right) {
        return false;
    }

    @Override
    public boolean isMultiplyOverflow(T left, T right) {
        return false;
    }
}
