package expression.generic.operators;

@FunctionalInterface
public interface TripleParser<T extends Number> {
    TripleExpression<T> parse(String expression) throws Exception;
}
