public class Permutations {
    public static void main(String...args){
        int[] d = new int[1,2,3];
        System.out.println(nextPerm(d));
    }
    int[] nextPerm(int[] digits){
        int [] r = digits;
        if(r[r.length-2] < r[r.length-1]){
            int temp = r[r.length-2];
            r[r.length-2] = r[r.length-1];
            r[r.length-1] = temp;
        }
        else{
            int right = r[r.length-1];
            int [] tempL = new int[r.length/2];
            int [] tempR = new int[r.length/2];
            int nR = 0;
            int nL = 0;
            int m = r.length-2;
            int mid = 0;
            int left = 0;
            for(int i = r.length-2; i>=0; i--){
                if(digits[i] == right+1){
                    mid = digits[i];
                }
                else{
                    if(i > digits.length/2){
                        tempR[nR] = r[m];
                        nR++;
                        m--;
                    }
                    else{
                        tempL[nL] = r[m];
                        nL++;
                        m--;
                    }
                }
            }
        }
    }
}
