package ASG6;
import java.util.*;

public class Ass6 {

    static class Instr {
        String res, a1, op, a2;
        boolean dead = false;

        Instr(String res, String a1, String op, String a2) {
            this.res = res; this.a1 = a1; this.op = op; this.a2 = a2;
        }

        public String toString() {
            return op == null ? res + " = " + a1 : res + " = " + a1 + " " + op + " " + a2;
        }
    }

    static boolean isNum(String s) {
        try { Integer.parseInt(s); return true; } catch (Exception e) { return false; }
    }

    // PASS 1: Constant Folding — compute constant expressions at compile time
    // e.g. t1 = 3 + 4  →  t1 = 7
    static void fold(List<Instr> code) {
        for (Instr i : code) {
            if (i.op == null || !isNum(i.a1) || !isNum(i.a2)) continue;
            int a = Integer.parseInt(i.a1), b = Integer.parseInt(i.a2);
            int r = i.op.equals("+") ? a+b : i.op.equals("-") ? a-b :
                    i.op.equals("*") ? a*b : a/b;
            i.a1 = String.valueOf(r); i.op = null; i.a2 = null;
        }
    }

    // PASS 2: Constant Propagation — replace variables holding constants with their value
    // e.g. x = 7, then later y = x + 1  →  y = 7 + 1
    static void propagate(List<Instr> code) {
        Map<String, String> known = new HashMap<>();
        for (Instr i : code) {
            if (known.containsKey(i.a1)) i.a1 = known.get(i.a1);
            if (i.a2 != null && known.containsKey(i.a2)) i.a2 = known.get(i.a2);
            if (i.op == null && isNum(i.a1)) known.put(i.res, i.a1);
            else known.remove(i.res);
        }
    }

    // PASS 3: Dead Code Elimination — remove instructions whose result is never used
    // e.g. t5 = 100  (if t5 is never read)  →  removed
    static void deadCode(List<Instr> code) {
        Set<String> used = new HashSet<>();
        used.add("out"); // seed with final output so it is never marked dead

        for (int i = code.size()-1; i >= 0; i--) {
            Instr ins = code.get(i);
            if (!used.contains(ins.res)) { ins.dead = true; continue; }
            if (!isNum(ins.a1)) used.add(ins.a1);
            if (ins.a2 != null && !isNum(ins.a2)) used.add(ins.a2);
        }
        code.removeIf(i -> i.dead);
    }

    // PASS 4: Common Subexpression Elimination — reuse already computed expressions
    // e.g. t3 = a+b, t4 = a+b  →  t4 = t3
    static void cse(List<Instr> code) {
        Map<String, String> seen = new HashMap<>(); // "a1 op a2" -> result var
        for (Instr i : code) {
            if (i.op == null) continue;
            String key = i.a1 + i.op + i.a2;
            if (seen.containsKey(key)) { i.a1 = seen.get(key); i.op = null; i.a2 = null; }
            else seen.put(key, i.res);
        }
    }

    static void print(String label, List<Instr> code) {
        System.out.println("\n-- " + label + " --");
        code.forEach(System.out::println);
    }

    public static void main(String[] args) {
        List<Instr> code = new ArrayList<>(Arrays.asList(
                new Instr("t1", "3",  "+", "4"),   // fold: 3+4 = 7
                new Instr("x",  "t1", null, null), // propagate: x = 7
                new Instr("t2", "x",  "+", "2"),   // propagate then fold: 7+2 = 9
                new Instr("t3", "a",  "+", "b"),   // CSE candidate
                new Instr("t4", "a",  "+", "b"),   // CSE: duplicate → t4 = t3
                new Instr("t5", "99", "+", "1"),   // dead: t5 never used
                new Instr("out","t3", null, null)  // output (keeps t3 alive)
        ));

        print("Original", code);

        fold(code);
        print("After Folding", code);

        propagate(code);
        fold(code); // re-fold so t2 = 7+2 becomes 9
        print("After Propagation", code);

        deadCode(code);
        print("After Dead Code Elim", code);

        cse(code);
        print("After CSE", code);
    }
}