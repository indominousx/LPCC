import java.io.*;
import java.util.*;

public class Main {

    static Map<String, List<String>> macros = new HashMap<>();
    static Map<String, List<String>> parameters = new HashMap<>();

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("src/input.txt"));

        String line;
        boolean isMacro = false;
        String macroName = "";

        List<String> body = new ArrayList<>();
        List<String> params = new ArrayList<>();

        while ((line = br.readLine()) != null) {

            line = line.trim();
            if (line.isEmpty())
                continue;

            String[] words = line.split("\\s+");

            // ===== MACRO START =====
            if (words[0].equals("MACRO")) {
                isMacro = true;
                macroName = words[1];

                params = new ArrayList<>();
                if (words.length > 2) {
                    String[] p = words[2].split(",");
                    for (String s : p)
                        params.add(s);
                }

                body = new ArrayList<>();
            }

            // MACRO END
            else if (words[0].equals("MEND")) {
                macros.put(macroName, body);
                parameters.put(macroName, params);
                isMacro = false;
            }

            // STORE BODY
            else if (isMacro) {
                body.add(line);
            }

            // MACRO CALL
            else {
                expand(line);
            }
        }

        br.close();
    }

    // EXPANSION FUNCTION
    static void expand(String line) {

        line = line.trim();
        if (line.isEmpty())
            return;

        String[] words = line.split("\\s+");

        // IF MACRO CALL
        if (macros.containsKey(words[0])) {

            String macroName = words[0];

            List<String> formalParams = parameters.get(macroName);
            List<String> actualParams = new ArrayList<>();

            if (words.length > 1) {
                String[] act = words[1].split(",");
                for (String s : act) {
                    actualParams.add(s.trim());
                }
            }

            // EXPAND BODY
            for (String s : macros.get(macroName)) {

                String temp = s;

                // Replace parameters
                for (int i = 0; i < formalParams.size(); i++) {
                    if (i < actualParams.size()) {
                        temp = temp.replace(formalParams.get(i), actualParams.get(i));
                    }
                }

                expand(temp); // recursive (nested macros)
            }

        } else {
            System.out.println(line);
        }
    }
}