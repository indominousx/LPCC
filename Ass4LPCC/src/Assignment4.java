import java.util.*;

public class Assignment4 {

    static Map<String, Integer> vars = new HashMap<>();

    public static void main(String[] args) {

        vars.put("a", 10);
        vars.put("b", 5);

        String expr = "a + b * 2";

        System.out.println("Result = " + evaluate(expr));

        // function example
        System.out.println("sqrt(16) = " + (int)Math.sqrt(16));
    }

    static int evaluate(String expr) {

        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {

            char ch = expr.charAt(i);

            if (ch == ' ') continue;

            // number
            if (Character.isDigit(ch)) {
                int num = ch - '0';
                values.push(num);
            }

            // variable
            else if (Character.isLetter(ch)) {
                values.push(vars.get(ch + ""));
            }

            // opening bracket
            else if (ch == '(') {
                ops.push(ch);
            }

            // closing bracket
            else if (ch == ')') {
                while (ops.peek() != '(') {
                    apply(values, ops.pop());
                }
                ops.pop();
            }

            // operator
            else {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(ch)) {
                    apply(values, ops.pop());
                }
                ops.push(ch);
            }
        }

        while (!ops.isEmpty()) {
            apply(values, ops.pop());
        }

        return values.pop();
    }

    static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    static void apply(Stack<Integer> values, char op) {

        int b = values.pop();
        int a = values.pop();

        if (op == '+') values.push(a + b);
        else if (op == '-') values.push(a - b);
        else if (op == '*') values.push(a * b);
        else if (op == '/') values.push(a / b);
    }
}