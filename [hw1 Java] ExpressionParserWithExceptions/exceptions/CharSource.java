package expression.exceptions;

public interface CharSource {
    boolean hasNext();

    char next();

    ExpressionException error(String message);

    void back(int size);

    char getCurrent();

    int getPosition();
}
