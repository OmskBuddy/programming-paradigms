package expression.generic.operators;

public interface Operator<T extends Number> {

    T add(T left, T right);
    T subtract(T left, T right);
    T multiply(T left, T right);
    T divide(T left, T right);
    T negate(T value);
    T getValue(int value);
    T getValue(T value);
    T parseToNumber(String s);
    T neutral();
    T abs(T value);
    T mod(T left, T right);
    boolean isAddOverflow(T left, T right);
    boolean isSubtractOverflow(T left, T right);
    boolean isMultiplyOverflow(T left, T right);
    boolean isDivideOverflow(T left, T right);
    boolean isModOverflow(T left, T right);

}
