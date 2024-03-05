package expression.generic;

import expression.generic.operators.*;

import java.math.BigInteger;
import java.text.Bidi;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Operator<? extends Number> operator = switch (mode) {
            case "i"  -> new CheckedIntegerOperator();
            case "d"  -> new DoubleOperator();
            case "bi" -> new BigIntegerOperator();
            case "u"  -> new IntegerOperator();
            case "l"  -> new LongOperator();
            case "s"  -> new ShortOperator();
            //:note: RE
            default -> throw new RuntimeException("no such option '" + mode + "'");
        };

        TripleParser<?> ep = new ExpressionParser<>(operator);
        TripleExpression<?> e = ep.parse(expression);

        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    result[i - x1][j - y1][k - z1] = e.evaluate(i, j, k);
                }
            }
        }

        return result;
    }
}
