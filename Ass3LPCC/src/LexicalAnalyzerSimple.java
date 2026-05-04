import java.util.*;

public class LexicalAnalyzerSimple {

    static Set<String> keywords = new HashSet<>(Arrays.asList(
            "int", "float", "if", "else", "while", "return", "for"
    ));

    static Set<String> operators = new HashSet<>(Arrays.asList(
            "+", "-", "*", "/", "=", "==", "<", ">", "<=", ">="
    ));

    static Set<String> nouns = new HashSet<>(Arrays.asList(
            "dog", "cat", "boy", "girl", "apple"
    ));

    static Set<String> verbs = new HashSet<>(Arrays.asList(
            "run", "eat", "play", "write"
    ));

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter input:");
        String input = sc.nextLine();

        String[] tokens = input.split(" ");

        System.out.println("\nTokens:");

        for (String token : tokens) {

            if (keywords.contains(token))
                System.out.println(token + " → Keyword");

            else if (operators.contains(token))
                System.out.println(token + " → Operator");

            else if (token.matches("\\d+"))
                System.out.println(token + " → Number");

            else if (nouns.contains(token))
                System.out.println(token + " → Noun");

            else if (verbs.contains(token))
                System.out.println(token + " → Verb");

            else if (token.matches("[a-zA-Z]+"))
                System.out.println(token + " → Identifier / Word");

            else
                System.out.println(token + " → Unknown");
        }
    }
}