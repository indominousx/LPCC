import java.util.Stack;

public class Assignment5 {

   static int tempCount = 1;

    public static void main(String[] args) {

        String expr = "a + b * c - d";
        String postfix = infixToPostfix(expr);
        generateTAC(postfix);
    }

    static int precedence(char ch){
        if(ch == '+' || ch == '-') return 1;
        if(ch == '*' || ch == '/') return 2;
        return 0;
    }

    public static String infixToPostfix(String expr){

        Stack<Character> stack = new Stack<>();
        String result = "";

        for(char ch : expr.toCharArray()){

            if(Character.isLetterOrDigit(ch)){
                result += ch;
            }

            else if(ch == ' ') continue;

            else {
                while(!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch)){
                    result += stack.pop();
                }

                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }


    static void generateTAC(String postfix){

        Stack<String> stack = new Stack<>();

        for(char ch : postfix.toCharArray()){

            if(Character.isLetterOrDigit(ch)){
                stack.push(String.valueOf(ch));
            }

            else{
                String b = stack.pop();
                String a = stack.pop();
                String temp = "t" + tempCount++;
                System.out.println(temp + " = " + a + " " + ch + " " + b);

                stack.push(temp);
            }
        }
    }
}
