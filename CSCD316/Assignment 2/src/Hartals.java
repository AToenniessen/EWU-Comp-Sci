import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Alex on 10/4/2016.
 */
public class Hartals {
    private static Scanner fin;

    public static void main(String...args){
        fin = new Scanner(System.in);


        int cases = fin.nextInt();
        while(cases > 0){
            int days = fin.nextInt();
            int parties = fin.nextInt();
            int hartals = 0;
            while(parties > 0) {
                parties--;
                int h = fin.nextInt();
                for (int n = 1; 1 < days; n++) {
                    if (n != 6 || (n-6) % 7 != 0&& n % 7 != 0 && n % h == 0)
                        hartals++;
                }
                System.out.printn(hartals);
                hartals = 0;
            }
            parties--;
        }
    }
}