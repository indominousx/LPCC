import java.io.*;
import java.util.*;

class Main {

    static ArrayList<String> sym = new ArrayList<>();
    static ArrayList<Integer> symAddr = new ArrayList<>();

    static ArrayList<String> lit = new ArrayList<>();
    static ArrayList<Integer> litAddr = new ArrayList<>();

    static ArrayList<Integer> pool = new ArrayList<>();

    static int LC = 0;

    // Add or update symbol
    static void addSymbol(String s, int addr) {
        int index = sym.indexOf(s);

        if (index == -1) {
            sym.add(s);
            symAddr.add(addr);
        } else {
            // update only if address was undefined
            if (symAddr.get(index) == -1 && addr != -1) {
                symAddr.set(index, addr);
            }
        }
    }

    // Add literal
    static void addLiteral(String l) {
        if (!lit.contains(l)) {
            lit.add(l);
            litAddr.add(-1);
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("src1/input.txt"));
        String line;

        pool.add(0);

        while ((line = br.readLine()) != null) {

            line = line.trim();
            if (line.length() == 0)
                continue;

            String parts[] = line.split("\\s+");

            // START
            if (parts[0].equals("START")) {
                LC = Integer.parseInt(parts[1]);
            }

            // ORIGIN
            else if (parts[0].equals("ORIGIN")) {
                if (parts[1].contains("+")) {
                    String temp[] = parts[1].split("\\+");
                    int index = sym.indexOf(temp[0]);
                    LC = symAddr.get(index) + Integer.parseInt(temp[1]);
                } else {
                    LC = Integer.parseInt(parts[1]);
                }
            }

            // LTORG / END
            else if (parts[0].equals("LTORG") || parts[0].equals("END")) {

                for (int i = pool.get(pool.size() - 1); i < lit.size(); i++) {
                    litAddr.set(i, LC++);
                }
                pool.add(lit.size());
            }

            // Label present
            else if (parts.length == 3) {

                if (parts[1].equals("EQU")) {
                    int ref = sym.indexOf(parts[2]);
                    addSymbol(parts[0], symAddr.get(ref));
                }

                else {
                    // assign label address only for DS/DC
                    addSymbol(parts[0], LC);

                    if (parts[1].equals("DS")) {
                        LC += Integer.parseInt(parts[2]);
                    }

                    else if (parts[1].equals("DC")) {
                        LC++;
                    }
                }
            }

            // Instruction
            else {

                if (parts.length > 1) {

                    if (parts[1].startsWith("=")) {
                        addLiteral(parts[1]);
                    } else {
                        addSymbol(parts[1], -1); // forward reference
                    }
                }
                LC++;
            }
        }

        br.close();

        // ===== OUTPUT =====

        System.out.println("\nSYMBOL TABLE:");
        for (int i = 0; i < sym.size(); i++) {
            System.out.println(sym.get(i) + " -> " + symAddr.get(i));
        }

        System.out.println("\nLITERAL TABLE:");
        for (int i = 0; i < lit.size(); i++) {
            System.out.println(lit.get(i) + " -> " + litAddr.get(i));
        }

        System.out.println("\nPOOL TABLE:");
        for (int i = 0; i < pool.size(); i++) {
            System.out.println(pool.get(i));
        }
    }
}