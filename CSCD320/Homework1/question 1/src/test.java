
/**
 * Created by Alex on 1/10/2017.
 */
public class test {
    private static void one(int a[], int j, int m)
    {
        int i, temp;
        if(j < m) {
            for( i = j; i <=m; i ++ ){
                if( a[i] < a[j] ) {
                    temp=a[i];
                    a[i]=a[j];
                    a[j]=temp;
                }
            }//end of for

            j++;
            one(a, j, m);
        }//end of outer if
    }//end of one method
    public static void main(String args[]) {
        int a[] = {2, 5, 1, 7, 9, 3, 6, 8};
        one(a, 0, a.length - 1);
        toString(a);
    }
    private static void toString(int[] ara){
        for(int i : ara){
            System.out.println(i);
        }
    }

}