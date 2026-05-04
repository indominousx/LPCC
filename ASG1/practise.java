package ASG1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class practise{

    //symbol table
    static ArrayList<String> sym = new ArrayList<>();
    static ArrayList<Integer> symAddr = new ArrayList<>();

    //literal table
    static ArrayList<String> lit = new ArrayList<>();
    static ArrayList<Integer> litAddr = new ArrayList<>();

    //pool table
    static ArrayList<Integer> pt = new ArrayList<>();

    static int LC = 0;

    public static void addSym(String s, int addr){
        int index = sym.indexOf(s);

        if(index == -1){
            sym.add(s);
            symAddr.add(addr);
        }else{
            if(symAddr.get(index) == -1 && addr!=-1){
                symAddr.set(index, addr);
            }
        }
    }

    public static void addLit(String l){
        lit.add(l);
        litAddr.add(-1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("ASG1/input.txt"));
        String line;

        pt.add(0);
        while((line = br.readLine())!=null){
            if(line.length()==0) continue;

            String[] parts = line.split("\\s+");
            if(parts[0].equals("START")){
                LC = Integer.parseInt(parts[1]);
            }

            else if(parts[0].equals("ORIGIN")){
                if(parts[1].contains("+")){
                    String[] temp = parts[1].split("\\+");
                    int index = sym.indexOf(temp[0]);
                    LC = symAddr.get(index) + Integer.parseInt(temp[1]);
                }else{
                    LC = Integer.parseInt(parts[1]);
                }
            }

            else if(parts[0].equals("LTORG") || parts[0].equals("END")){
                for(int i=pt.get(pt.size()-1) ; i<lit.size();i++){
                    litAddr.set(i,LC++);
                }
                pt.add(lit.size());
            }

            else if(parts.length == 3){

                if(parts[1].equals("EQU")){

                    int idx = sym.indexOf(parts[2]);
                    int addr = symAddr.get(idx);
                    addSym(parts[0],addr);

                }else{
                    addSym(parts[0],LC);
                    if(parts[1].equals("DC")){
                        LC++;
                    }
                    else if(parts[1].equals("DS")){
                        LC = LC + Integer.parseInt(parts[2]);
                    }
                }
            }

            else if(parts.length > 1){
                if(parts[1].charAt(0)=='='){
                    addLit(parts[1]);
                }
                else{
                    addSym(parts[1], -1);
                }
                LC++;
            }
        }

        br.close();

        System.out.println("\nSYMBOL TABLE:");
        for (int i = 0; i < sym.size(); i++) {
            System.out.println(sym.get(i) + " -> " + symAddr.get(i));
        }

        System.out.println("\nLITERAL TABLE:");
        for (int i = 0; i < lit.size(); i++) {
            System.out.println(lit.get(i) + " -> " + litAddr.get(i));
        }

        System.out.println("\nPOOL TABLE:");
        for (int i = 0; i < pt.size(); i++) {
            System.out.println(pt.get(i));
        }

    }
}