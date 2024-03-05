package expression.exceptions;

public class DBZException extends ExpressionException {
    public DBZException() {
        super("division by zero");
    }
}
