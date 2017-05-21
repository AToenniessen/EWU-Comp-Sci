import java.util.Scanner;

/**
 * Created by ajt11 on 10/21/2016.
 */
public class Jeopardy {
    public static void main(String[]args){
        Scanner fin = new Scanner(System.in);
        while (fin.hasNextLine()){
            int pal = 0;
            int digit = fin.nextInt();
            int base = fin.nextInt();
            for(int i=1; i < Integer.MAX_VALUE; i++){
                if(String.valueOf(i).length() <= digit){
                    if(isPrimeMod(i, base)){
                        if(String.valueOf(i).compareTo(new StringBuilder(i).reverse().toString()) == 0) {
                            pal++;
                        }
                    }
                }
            }
            System.out.printf("The number of %d-digit palindromic primes < 2^31 in base %d.\nWhat is %d?\n", digit, base, pal);
        }
    }
    private static boolean isPrimeMod(int n, int base){
        for(int i = 1; i < n/2; i++){
            if(Integer.parseInt(Integer.toString(n), base) % Integer.parseInt(Integer.toString(i), base) == 0){
                return false;
            }
        }
        return true;
    }
}
