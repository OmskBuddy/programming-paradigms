package expression.exceptions;

import expression.*;


public class ExpressionParser implements TripleParser {
    @Override
    public CommonExpression parse(String expression) throws Exception {
        expression += " ";
        return new MainParser(new StringSource(expression)).getExpr();
    }

    private static class MainParser extends BaseParser {
        private enum Operation {
            SET, CLEAR, GCD, LCM, PLUS, MINUS, MULT, DIV, CLOSE, NOTHING
        }

        protected MainParser(CharSource source) {
            super(source);
        }

        public CommonExpression getExpr() {
            CommonExpression expr = getThirdLevel();
            if (eof()) {
                return expr;
            }
            throw error("Expected end of expression, but there is " + getChar());
        }

        public CommonExpression getThirdLevel() {
            CommonExpression result = getSecondLevel();

            while (true) {
                Operation op = getOperation();
                switch (op) {
                    case CLOSE -> {
                        backOperation(op);
                        return result;
                    }
                    case SET -> {
                        result = new Set(result, getSecondLevel());
                    }
                    case CLEAR -> {
                        result = new Clear(result, getSecondLevel());
                    }
                    case GCD -> {
                        result = new CheckedGCD(result, getSecondLevel());
                    }
                    case LCM -> {
                        result = new CheckedLCM(result, getSecondLevel());
                    }
                    case NOTHING -> {
                        return result;
                    }
                    default -> throw error("Unexpected operation in Common Mult/Div level: " + op);
                }
            }
        }

        public CommonExpression getSecondLevel() {
            CommonExpression result = getFirstLevel();

            while (true) {
                Operation op = getOperation();
                switch (op) {
                    case SET, CLEAR, GCD, LCM, CLOSE, NOTHING -> {
                        backOperation(op);
                        return result;
                    }
                    case PLUS -> {
                        result = new CheckedAdd(result, getFirstLevel());
                    }
                    case MINUS -> {
                        result = new CheckedSubtract(result, getFirstLevel());
                    }
                    default -> throw error("Unexpected operation in Plus/Minus level: " + op);
                }
            }
        }

        public CommonExpression getFirstLevel() {
            CommonExpression result = getZeroLevel();

            while (true) {
                Operation op = getOperation();
                switch (op) {
                    case SET, CLEAR, GCD, LCM, PLUS, MINUS, CLOSE, NOTHING -> {
                        backOperation(op);
                        return result;
                    }
                    case MULT -> {
                        result = new CheckedMultiply(result, getZeroLevel());
                    }
                    case DIV -> {
                        result = new CheckedDivide(result, getZeroLevel());
                    }
                    default -> throw error("Unexpected operation in Mult/Div level: " + op);
                }
            }
        }

        public CommonExpression getZeroLevel() {
            skipWhitespaces();

            if (take('-')) {
                if (takeWhitespace()) {
                    return new CheckedNegate(getZeroLevel());
                }
                if (between('0', '9')) {
                    StringBuilder sb = new StringBuilder();
                    takeDigits(sb);
                    return new CheckedConst("-" + sb);
                } else {
                    return new CheckedNegate(getZeroLevel());
                }
            } else if (take("reverse")) {
                testEnding();
                return new CheckedReverse(getZeroLevel());
            } else if (take("count")) {
                testEnding();
                return new CheckedCount(getZeroLevel());
            } else if (take("pow10")) {
                testEnding();
                return new CheckedPow(getZeroLevel());
            } else if (take("log10")) {
                testEnding();
                return new CheckedLog10(getZeroLevel());
            } else if (between('0', '9')) {
                StringBuilder sb = new StringBuilder();
                takeDigits(sb);
                return new CheckedConst(sb.toString());
            } else if (between('x', 'z')) {
                return new Variable(take());
            } else if (take('(')) {
                CommonExpression result = getThirdLevel();
                expect(')');
                return result;
            }

            throw error("Unexpected symbol in elements level: " + getChar());
        }

        private void takeDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(take());
            }
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
            } else if (take("gcd")) {
                testEnding();
                return Operation.GCD;
            } else if (take("lcm")) {
                testEnding();
                return  Operation.LCM;
            } else if (take("set")) {
                testEnding();
                return Operation.SET;
            } else if (take("clear")) {
                testEnding();
                return Operation.CLEAR;
            } else if (take(')')) {
                return Operation.CLOSE;
            } else if (eof()) {
                return Operation.NOTHING;
            }

            throw error("Unexpected symbol in getting operation level: " + getChar());
        }

        private void backOperation(Operation op) {
            int size = getOperationSize(op);
            back(size);
        }

        private int getOperationSize(Operation op) {
            int result = switch (op) {
                case PLUS, MINUS, MULT, DIV, CLOSE -> 1;
                case SET, GCD, LCM -> 3;
                case CLEAR -> 5;
                case NOTHING -> 0;
            };
            return result;
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
