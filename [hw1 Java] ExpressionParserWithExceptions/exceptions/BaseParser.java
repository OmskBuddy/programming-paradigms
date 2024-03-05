package expression.exceptions;

public class BaseParser {
    private static final char END = '\0';
    final CharSource source;

    public char getChar() {
        return ch;
    }

    private char ch = 0xffff;

    protected BaseParser(final CharSource source) {
        this.source = source;
        take();
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean take(final String expected) {
        int size = 0;
        for (final char c : expected.toCharArray()) {
            if (take(c)) {
                size++;
            } else {
                back(size);
                return false;
            }
        }
        return true;
    }

    protected boolean takeWhitespace() {
        if (Character.isWhitespace(ch)) {
            take();
            return true;
        }
        return false;
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean eof() {
        take();
        return take(END);
    }

    protected ExpressionException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected void back(int size) {
        source.back(size);
        ch = source.getCurrent();
    }
}
