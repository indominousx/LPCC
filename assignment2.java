import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class MacroProcessor {

    static Map<String, Integer> MNT = new LinkedHashMap<>();
    static List<String> MDT = new ArrayList<>();
    static Map<String, List<String>> ParamTab = new HashMap<>();

    public static void main(String[] args) throws Exception {

        BufferedReader bf = new BufferedReader(new FileReader("input.txt"));

        String line;

        boolean isMacro = false;
        boolean isHeader = false;
        String currentMacro = "";
        List<String> params = null;

        // ================= PASS 1 =================
        while ((line = bf.readLine()) != null) {

            if (line.trim().isEmpty()) continue;

            String[] words = line.trim().split("\\s+");

            // Detect MACRO start
            if (words[0].equals("MACRO")) {
                isMacro = true;
                isHeader = true;
                continue;
            }

            if (isMacro) {

                // Header processing
                if (isHeader) {
                    currentMacro = words[0];
                    MNT.put(currentMacro, MDT.size());

                    params = new ArrayList<>();

                    for (int i = 1; i < words.length; i++) {
                        params.add(words[i]);
                    }

                    ParamTab.put(currentMacro, params);
                    isHeader = false;
                }

                // Body processing
                else {

                    if (words[0].equals("MEND")) {
                        MDT.add("MEND");
                        isMacro = false;
                        currentMacro = "";
                        continue;
                    }

                    String modifiedLine = line;

                    for (int i = 0; i < params.size(); i++) {
                        String index = "#" + (i + 1);
                        modifiedLine = modifiedLine.replace(params.get(i), index);
                    }

                    MDT.add(modifiedLine);
                }
            }
        }

        // Print tables
        System.out.println("MNT: " + MNT);
        System.out.println("MDT: " + MDT);
        System.out.println("ParamTAB: " + ParamTab);

        // ================= PASS 2 =================
        bf = new BufferedReader(new FileReader("input.txt"));

        System.out.println("Expanded Code:");

        while ((line = bf.readLine()) != null) {

            if (line.trim().isEmpty()) continue;

            process(line.trim());
        }
    }

    // ================= EXPANSION FUNCTION =================
    static void process(String line) {

        String[] words = line.split("\\s+");

        // If macro call
        if (MNT.containsKey(words[0])) {

            String macroName = words[0];
            int MDTIndex = MNT.get(macroName);

            List<String> params = ParamTab.get(macroName);

            String[] args;

            if (words.length > 1) {
                args = words[1].split(",");
            } else {
                args = new String[0];
            }

            while (true) {

                String mdtLine = MDT.get(MDTIndex);

                if (mdtLine.equals("MEND")) {
                    break;
                }

                String temp = mdtLine;

                // Replace parameters
                for (int i = 0; i < args.length; i++) {
                    String index = "#" + (i + 1);
                    temp = temp.replace(index, args[i]);
                }

                // Recursive expansion (nested macros)
                process(temp);

                MDTIndex++;
            }
        }

        // Normal line
        else if (!line.equals("MACRO") && !line.equals("MEND")) {
            System.out.println(line);
        }
    }
}