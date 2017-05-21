import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by ajt11 on 10/21/2016.
 */
public class BogusDivision {
    public static void main(String...args){
    for(int den = 101; den <= 999; den++){
        for(int num = 100; num <= 999 && den > num; num++){
            if((num%100)%10 == den/100){
                int n = num/10;
                int d = den%100;
                if(d == 0)
                    break;
                else if(n == d)
                    break;
                BigDecimal comp = new BigDecimal(num / den);
                BigDecimal comp2 = new BigDecimal(n / d);
                if(comp.compareTo(comp2) == 0){
                    System.out.format("%d / %d = %d / %d\n", num, den, n, d);
                }
            }
        }
    }
    }
}