import java.util.*;

public class Assignment6 {

    public static void main(String[] args) {

        List<String> code = new ArrayList<>();

        code.add("t1 = 2 + 3");
        code.add("t2 = t1");
        code.add("t3 = t2 * 4");
        code.add("t4 = t3");
        code.add("t5 = t4");
        code.add("t6 = 10");

        System.out.println("Original Code:");
        for (String line : code) {
            System.out.println(line);
        }

        System.out.println("Optimised code");

        Map<String, Integer> constantValues = new HashMap<>();
        List<String> optimised = new ArrayList<>();

        // Pass 1 Constant Folding + Copy propagation

        for(String line : code){

            String[] parts = line.split(" ");

            if(parts.length == 5 &&  parts[2].matches("\\d+") &&
            parts[4].matches("\\d+")){


                int a = Integer.parseInt(parts[2]);
                int b = Integer.parseInt(parts[4]);

                int result = 0;

                if(parts[3].equals("+")) result = a+b;
                else if(parts[3].equals(("-"))) result = a - b;
                else if(parts[3].equals("*")) result = a * b;
                else if(parts[3].equals(("/"))) result = a / b;

                constantValues.put(parts[0] , result);
                optimised.add(parts[0] + " = " + result);

            }


            else if(parts.length == 3){

                String rhs = parts[2];

                if(constantValues.containsKey(rhs)){
                    int val = constantValues.get(rhs);
                    constantValues.put(parts[0] , val);
                    optimised.add(parts[0] + " = " + val);
                }

                else{
                    optimised.add(line);
                }
            }

            else if(parts.length == 5){

                String op1 = parts[2];
                String op2 = parts[4];

                if(constantValues.containsKey(op1)){
                    op1 = constantValues.get(op1) + "";
                }

                if(constantValues.containsKey(op2)){
                    op2 = constantValues.get(op2) + "";
                }

                optimised.add(parts[0] + " = " + op1 + " " + parts[3] + " " + op2);
            }
        }


        Set<String> used = new HashSet<>();

        for(String line : optimised){

            String [] parts = line.split(" ");

            for(int i = 2; i < parts.length; i++){
                if(parts[i].matches("t\\d+")){
                    used.add(parts[i]);
                }
            }
        }

        String lastLine = optimised.get(optimised.size() - 1);
        String finalVar = lastLine.split(" ")[0];

        for (String line : optimised) {

            String lhs = line.split(" ")[0];

            if (used.contains(lhs)) {
                System.out.println(line);
            }
        }
    }
}
