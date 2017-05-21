import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Alexander Toenniessen Due Friday September 30th
 *
 * edge cases handled include:
 * 1 input
 * rounding to .00 decimal places
 * $10000 input
 * random assortment of other values with "correct" outputs
 */
public class TheTrip {
    private static Scanner fin;

    public static void main(String...args){
        String file;
        if(args.length != 0){
            file = args[0];
        }else{
            file = "input.txt";
        }
        fin = openFin(file);
        if(fin == null)
            System.out.println("The input file does not exist.\n\nExiting...");
        else{
            int n;
            while((n = fin.nextInt()) != 0) {
                if(n == 1) {
                    System.out.print("\n$0.00");
                    fin.nextDouble();
                }
                else
                    System.out.printf("\n$%.2f", readValues(n));
            }
        }
    }
    private static double readValues(int n){
        double[] costs = new double[n];
        int i = 0;
        while (n != 0) {
            n--;
            costs[i] = fin.nextDouble();
            i++;
        }
        return calcCosts(costs);
    }
    private static double calcCosts(double[] c){
        int NumStudents= 0;
        double tot = 0.0;
        double average, NegSum = 0, PosSum = 0;
        while(NumStudents< c.length){
            tot += c[NumStudents];
            NumStudents++;
        }
        average = Math.round((tot/ NumStudents) * 100.0) / 100.0;
        double owed = 0;
        NumStudents= 0;
        while(NumStudents < c.length){
            owed = c[NumStudents] - average;
            if(owed < 0){
                NegSum -= owed;
            }
            else
                PosSum += owed;
            NumStudents++;
        }
        if(NegSum == 0)
            return average;
        else if(NegSum < PosSum)
            return NegSum;
        else
            return PosSum;
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
