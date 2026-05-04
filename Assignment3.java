import java.util.*;

public class Assignment3 {

    public static void main(String[] args) {

        String input = "int x = 10; x = x + 5;";

        String[] words = input.split("\\s+");

        Set<String> symbolTable = new HashSet<>();

        for (String w : words) {

            if (w.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                symbolTable.add(w);
                System.out.println(w + " : Identifier");
            }

            else if (w.matches("[0-9]+")) {
                System.out.println(w + " : Number");
            }

            else {
                System.out.println(w + " : Symbol");
            }
        }

        System.out.println("\nSymbol Table:");
        for (String s : symbolTable) {
            System.out.println(s);
        }
    }
}