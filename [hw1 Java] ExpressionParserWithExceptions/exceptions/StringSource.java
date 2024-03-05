package expression.exceptions;

public class StringSource implements CharSource {
    private final String data;

    private int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public ExpressionException error(final String message) {
        return new ExpressionException(pos + ": " + message);
    }

    @Override
    public void back(int size) {
        pos -= size;
    }

    @Override
    public char getCurrent() {
        return data.charAt(pos - 1);
    }

    @Override
    public int getPosition() {
        return pos - 1;
    }
}
