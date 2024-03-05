package expression;

import expression.parser.CharSource;

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
        //System.err.print(pos + ": ");
        return data.charAt(pos++);
    }

    @Override
    public IllegalArgumentException error(final String message) {
        return new IllegalArgumentException(pos + ": " + message);
    }

    @Override
    public void back(int size) {
        pos -= size;
    }

    @Override
    public char getCurrent() {
        return data.charAt(pos - 1);
    }
}
