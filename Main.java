import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

class Main{

    static Map<String,Integer> symtab = new HashMap<>();
    static List<String> literalTab = new ArrayList<>();
    static List<Integer> literalAddress = new ArrayList<>();
    static List<Integer> poolTab = new ArrayList<>();
    static int LC = 0;

    public static void main(String[] args) throws Exception {

        BufferedReader bf = new BufferedReader(new FileReader("file.txt"));

        poolTab.add(0);

        String line;

        while((line = bf.readLine()) != null){

            String[] words = line.trim().split("\\s+");;

            if(words[0].equals("START")){
                LC = Integer.parseInt(words[1]);
            }

            else if(words[0].equals("END") || words[0].equals("LTORG")){

                boolean newPool = false;

                for(int i = 0; i < literalTab.size(); i++){
                    if(literalAddress.get(i) == -1) {
                        literalAddress.set(i, LC);
                        LC++;
                        newPool = true;
                    }
                }


                if(newPool){
                    poolTab.add(literalTab.size());
                }


            }

            else{
                if(!words[0].equals("MOVER") &&
                   !words[0].equals("ADD") &&
                   !words[0].equals("SUB") &&
                   !words[0].equals("MOVEM")&&
                   !words[0].equals("BC")){
                    symtab.put(words[0] , LC);
                }

                for(String w : words){
                    if(w.contains("=")){
                        literalTab.add(w);
                        literalAddress.add(-1);
                    }
                }

                LC++;
            }
        }


        System.out.println("Symbol Table");
        for(String s : symtab.keySet()){
            System.out.println(s + " -> " + symtab.get(s));
        }

        System.out.println("LITERAL TABLE:");
        for (int i = 0; i < literalTab.size(); i++) {
            System.out.println(literalTab.get(i) + " -> " + literalAddress.get(i));
        }

        System.out.println("POOL TABLE:");
        for (int p : poolTab) {
            System.out.println(p);
        }





    }
}