import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) {
        HashSet<String> dictionary;
        dictionary = readFile();
        Tree test = new Tree();
        Scanner kb = new Scanner(System.in);
        System.out.println("Pretend this is a keypad on an old flip phone");
        Integer[] keypad = displayMenu(kb);
        while(keypad.length != 0){
            test.insert(keypad);
            printWords(test.findAllWordsHash(dictionary), "Hashing");
            Trie preFix = new Trie();
            preFix.createPreFix(dictionary);
            printWords(test.findAllWordsPreFix(preFix), "Prefix Tree");
            keypad = displayMenu(kb);
        }
        System.out.println("Exiting...");
    }
    private static Integer[] displayMenu(Scanner kb){
        System.out.print("\n0 = exit program\n1 = finish input\n2 = 'a''b''c'\n" +
                "3 = 'd''e''f'\n4 = 'g''h''i'\n5 = 'j''k''l'\n6 = 'm''n''o'\n7 = 'p''q''r''s'\n8 = 't''u''v'\n9 = 'w''x''y''z\n\n");
        System.out.print("Remember, when finished with input press 1\nif you wish to exit program, press 0\n\nInput one number at a time : ");
        ArrayList<Integer> temp = new ArrayList<>();
        Integer i = kb.nextInt();
        while(i > 1){
            temp.add(i);
            i = kb.nextInt();
        }
        return toInteger(temp.toArray());
    }
    private static Integer[] toInteger(Object[] t){
        Integer[] in = new Integer[t.length];
        int i = 0;
        for(Object o : t) {
            in[i] = (Integer) o;
            i++;
        }
        return in;
    }
    private static HashSet<String> readFile(){
        HashSet<String> temp = new HashSet<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader("Dictionary"));
            while(in.ready()){
                temp.add((in.readLine().split(","))[0]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return temp;
    }
    private static void printWords(ArrayList<String> a, String m){
        System.out.println("Found words for user input include using " + m + ": ");
        for(String s : a)
            System.out.println(s);
    }
}
