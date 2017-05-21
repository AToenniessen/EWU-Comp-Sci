import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
Alexander Toenniessen
Both maximums and minimums are tested
random assortment of other inputs are tested
 */
class Hartals {

    public static void main(String...args)throws IOException{
        BufferedReader fin = new BufferedReader(new InputStreamReader(System.in));
        int cases = Integer.parseInt(fin.readLine());
        while(cases > 0){
            int days = Integer.parseInt(fin.readLine());
            int parties = Integer.parseInt(fin.readLine());
            int hartals = 0;
            int [] time = new int[days + 1];
            while(parties > 0) {
                parties--;
                int h = Integer.parseInt(fin.readLine());
                for(int n = 1; n <= days; n ++){
                    if(n % 7 != 6 && n % 7 != 0 && n % h == 0 && time[n] == 0){
                        time[n] = 1;
                        hartals++;
                    }
                }
            }
            System.out.println(hartals);
            cases--;
        }
    }
}