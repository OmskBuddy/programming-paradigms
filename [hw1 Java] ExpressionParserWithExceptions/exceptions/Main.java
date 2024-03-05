package expression.exceptions;

public class Main {
    public static void main(String[] args) {
        String s = "log10(2147483647)";
        ExpressionParser p = new ExpressionParser();
        try {
            System.out.println(p.parse(s).evaluate(0, 0, 0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
