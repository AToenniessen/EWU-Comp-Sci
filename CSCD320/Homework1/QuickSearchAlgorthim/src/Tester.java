import java.util.Arrays;

public class Tester {
    public static void main(String... args) {
        int[] array = {1, 3, 5, 7, 9, 14, 16, 19};
        System.out.println("Using an input array A = " + Arrays.toString(array));
        int val;
        val = QuickSearchCall(array, 8);
        callPrint(array, val, 8);
        val = QuickSearchCall(array, 19);
        callPrint(array, val, 19);
        val = QuickSearchCall(array, 20);
        callPrint(array, val, 20);
        val = QuickSearchCall(array, 6);
        callPrint(array, val, 6);
        val = QuickSearchCall(array, -1);
        callPrint(array, val, -1);
    }
    private static void callPrint(int [] a, int val, int t) {
        if(val >= 0)
            printTrue(a, val, t);
        else
            printFalse(val, t);
    }
    private static void printTrue(int [] a, int val, int t){
        System.out.printf("performing quickSearch(A, %d), it returns %d, the index of number %d.\n", t, val, a[val]);
    }
    private static void printFalse(int val, int t){
        System.out.printf("performing quickSearch(A, %d), it return %d, indicating there is no such value that is \n" +
                "bigger than or equal to %d in array A.\n", t, val, t);
    }
    private static int QuickSearchCall(int[] a, int t) {
        if(t < 0)
            return 0;
        else if(t > a[a.length-1])
            return -1;
        else
            return QuickSearchAlgorithm(a, t, 0, a.length-1, (a.length-1)/2);
    }
    private static int QuickSearchAlgorithm(int[] a, int t, int beg, int end, int mid){
        if(a[mid] < t && beg != mid)
            return QuickSearchAlgorithm(a, t, mid, end, (mid + end)/2);
        else if(a[mid] > t && beg != mid) {
            return QuickSearchAlgorithm(a, t, beg, mid, (beg + mid)/2);
        }
        else
          return mid + 1;

    }

}
