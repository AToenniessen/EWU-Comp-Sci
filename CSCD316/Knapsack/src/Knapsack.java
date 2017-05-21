import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Knapsack {

    public static int backWeight, provisions, backPacks;
    public static int[] provisionWeight, benefit;
    public static int[][] ks;

    public static void main(String...args){
        Scanner kb = new Scanner(System.in);
        String file;
        if(args.length != 0){
            file = args[0];
        }else {
            System.out.print("Please enter the name of the file to process: ");
            file = kb.nextLine();
        }
        Scanner fin = openFin(file);
        if(fin == null)
            System.out.println("File may not exist.... GoodBye");
        else{
            backPacks = fin.nextInt();
            int t = 0;
            while(t < backPacks) {
                readFin(fin);
                doWork();
                printRes();
                t++;
            }
        }
    }
    private static void printRes(){
        int maxW = 0;
        int maxB = 0;
        for(int c = benefit.length; c > 0; c--){
            for(int r = backWeight; r > 0; r--){
                if(maxW == 0 && maxB == 0) {
                    maxW = r;
                    maxB = ks[c][r];
                }
                else if(maxB < ks[c][r]){
                    maxB = ks[c][r];
                    maxW = r;
                }
            }
        }
        System.out.println("Backpackweight: " + backWeight + ", Loaded weight: " + maxW + ", Benefit: " + maxB);
    }
    private static void doWork(){
        ks = new int[benefit.length + 1][backWeight + 1];

        for(int cur = 1; cur <= provisions; cur++){
            for(int curWght = 1; curWght <= backWeight; curWght++){
                if(provisionWeight[cur - 1] <= curWght){
                    ks[cur][curWght] = Math.max(benefit[cur - 1] + ks[cur - 1][curWght - provisionWeight[cur - 1]], ks[cur - 1][curWght]);
                }
                else
                    ks[cur][curWght] = ks[cur - 1][curWght];
            }
        }
    }
    private static void readFin(Scanner fin){
        backWeight = fin.nextInt();
        provisions = fin.nextInt();
        int t = 0;
        provisionWeight = new int[provisions];
        benefit = new int[provisions];
        while(t < provisions){
            provisionWeight[t] = fin.nextInt();
            benefit[t] = fin.nextInt();
            t++;
        }
    }
    private static Scanner openFin(String f){
        try{
            return new Scanner(new File(f));
        }catch(FileNotFoundException e){
            e.getMessage();
        }
        return null;
    }
}
