package expression.generic;

import expression.exceptions.BaseParser;
import expression.exceptions.CharSource;
import expression.exceptions.StringSource;
import expression.generic.operators.*;

public class ExpressionParser<T extends Number> implements TripleParser<T> {

    Operator<T> operator;
    public ExpressionParser(Operator<T> operator) {
        this.operator = operator;
    }

    @Override
    public TripleExpression<T> parse(String expression) {
        expression += " ";
        return new Parser(new StringSource(expression)).getExpr();
    }

    private class Parser extends BaseParser {
        protected Parser(CharSource source) {
            super(source);
        }

        public TripleExpression<T> getExpr() {
            TripleExpression<T> expr = getSecondLevel();
            if (eof()) {
                return expr;
            }
            throw error("Expected end of expression, but there is " + getChar());
        }

        public TripleExpression<T> getSecondLevel() {
            TripleExpression<T> result = getFirstLevel();

            while (true) {
                Operation op = getOperation();
                switch (op) {
                    case CLOSE, NOTHING -> {
                        backOperation(op);
                        return result;
                    }
                    case PLUS-> {
                        result = new Add<>(result, getFirstLevel(), operator);
                    }
                    case MINUS -> {
                        result = new Subtract<>(result, getFirstLevel(), operator);
                    }
                    default -> throw error("Unexpected operation in Common Mult/Div level: " + op);
                }
            }
        }

        public TripleExpression<T> getFirstLevel() {
            TripleExpression<T> result = getZeroLevel();

            while (true) {
                Operation op = getOperation();
                switch (op) {
                    case PLUS, MINUS, CLOSE, NOTHING -> {
                        backOperation(op);
                        return result;
                    }
                    case MULT -> {
                        result = new Multiply<>(result, getZeroLevel(), operator);
                    }
                    case MOD -> {
                        result = new Mod<>(result, getZeroLevel(), operator);
                    }
                    case DIV -> {
                        result = new Divide<>(result, getZeroLevel(), operator);
                    }
                    default -> throw error("Unexpected operation in Mult/Div level: " + op);
                }
            }
        }

        public TripleExpression<T> getZeroLevel() {
            skipWhitespaces();

            if (take('-')) {
                if (takeWhitespace()) {
                    return new Negate<>(getZeroLevel(), operator);
                }
                if (between('0', '9')) {
                    StringBuilder sb = new StringBuilder();
                    takeDigits(sb);
                    return new Const<>("-" + sb, operator);
                } else {
                    return new Negate<>(getZeroLevel(), operator);
                }
            } else if (take("abs")) {
                testEnding();
                return new Abs<>(getZeroLevel(), operator);
            } else if (take("square")) {
                testEnding();
                return new Square<>(getZeroLevel(), operator);
            } else if (between('0', '9')) {
                StringBuilder sb = new StringBuilder();
                takeDigits(sb);
                return new Const<>(sb.toString(), operator);
            } else if (between('x', 'z')) {
                return new Variable<>(take(), operator);
            } else if (take('(')) {
                TripleExpression<T> result = getSecondLevel();
                expect(')');
                return result;
            }

            throw error("Unexpected symbol in elements level: " + getChar());
        }

        private Operation getOperation() {
            skipWhitespaces();

            if (take('+')) {
                return Operation.PLUS;
            } else if (take('-')) {
                return Operation.MINUS;
            } else if (take('*')) {
                return Operation.MULT;
            } else if (take('/')) {
                return Operation.DIV;
            } else if (take(')')) {
                return Operation.CLOSE;
            } else if (take("mod")) {
                testEnding();
                return Operation.MOD;
            } else if (eof()) {
                return Operation.NOTHING;
            }

            throw error("Unexpected symbol in getting operation level: " + getChar());
        }

        private void takeDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(take());
            }
        }

        private void backOperation(Operation op) {
            int size = getOperationSize(op);
            back(size);
        }

        private int getOperationSize(Operation op) {
            return switch (op) {
                case PLUS, MINUS, MULT, DIV, CLOSE -> 1;
                case MOD -> 3;
                default -> 0;
            };
        }

        private void skipWhitespaces() {
            while (takeWhitespace()) {
                // skip
            }
        }

        private void testEnding() {
            if (between('0', '9') || between('a', 'z')) {
                throw error("Unexpected symbol of operation's ending: " + getChar());
            }
        }
    }
}
