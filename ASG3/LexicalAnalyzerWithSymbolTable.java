package ASG3;

import java.util.*;

public class LexicalAnalyzerWithSymbolTable {

    static Set<String> keywords = new HashSet<>(Arrays.asList(
            "int", "float", "if", "else", "while", "return"
    ));

    static Set<String> operators = new HashSet<>(Arrays.asList(
            "+", "-", "*", "/", "="
    ));

    static Map<String, String> symbolTable = new LinkedHashMap<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter input:");
        String input = sc.nextLine();

        String[] tokens = input.split(" ");

        System.out.println("\nTokens:");

        for (String token : tokens) {

            if (keywords.contains(token)) {
                System.out.println(token + " → Keyword");
            }

            else if (operators.contains(token)) {
                System.out.println(token + " → Operator");
            }

            else if (token.matches("\\d+")) {
                System.out.println(token + " → Number");
            }

            else if (token.matches("[a-zA-Z]+")) {

                System.out.println(token + " → Identifier");

                // Add to symbol table
                if (!symbolTable.containsKey(token)) {
                    symbolTable.put(token, "id" + (symbolTable.size() + 1));
                }
            }

            else {
                System.out.println(token + " → Unknown");
            }
        }

        // Print Symbol Table
        System.out.println("\nSymbol Table:");
        for (Map.Entry<String, String> entry : symbolTable.entrySet()) {
            System.out.println(entry.getValue() + " → " + entry.getKey());
        }
    }
}