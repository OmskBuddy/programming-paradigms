package expression.generic;

public class Main {
    public static void main(String[] args) {
        String mode = args[0];
        String expression = args[1];
        int start = -2;
        int end = 2;

        Tabulator tab = new GenericTabulator();
        try{
            Object[][][] res = tab.tabulate(mode, expression, start, end, start, end, start, end);
            for (int i = 0; i < res.length; i++) {
                for (int j = 0; j < res[i].length; j++) {
                    for (int k = 0; k < res[i][j].length; k++) {
                        System.out.println(i + " " + j + " " + k + " " + res[i][j][k]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Hello, kitty! You have a problem with your tabulator. \n" +
                    "I believe you will fix it!");
        }

    }
}
