import java.util.Arrays;

//Extra credit attempted

public class Permutations {
    public static void main(String... args) {
        int[][] d = {{9,5,7,8,3,2,4,6},{1,1,2,5,8,7,6},{1, 3, 2},
                {1},{1, 2},{1, 2, 3},{2, 3, 1},{2, 1, 8, 7, 6, 5},
                {1, 2, 3, 4},{2, 5, 4, 3, 1},{3, 1, 2, 4, 5},
                {5, 6, 7, 8, 9},{6, 5, 4, 3, 2, 1},{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {1,1,3,4,6,5,4,3,8,9}};
        for (int[] x : d) {
            System.out.println(Arrays.toString(x));
            System.out.println(Arrays.toString(nextPerm(x)) + "\n");
        }
    }

    private static int[] nextPerm(int[] digits) {
        int size = digits.length;
        int[] temp = new int[size];
        int[] res = new int[size];
        int split = 0;
        int pivot;
        int prev = -1 * Integer.MAX_VALUE;
        int n = 0;
        int i;
        for (i = size - 1; i >= 0; i--) {
            if (digits[i] >= prev) {
                temp[n] = digits[i];
                prev = digits[i];
                n++;
            } else {
                split = digits[i];
                break;
            }
        }
        if (i == -1)
            return temp;
        pivot = digits[i + 1];
        for (n = 0; n < size; n++) {
            if (temp[n] > split && temp[n] < pivot) {
                pivot = temp[n];
            }
        }
        for (n = 0; n < size; n++) {
            if (digits[n] != split)
                res[n] = digits[n];
            else
                break;
        }
        res[n] = pivot;
        for (i = 0, n += 1; n < size; i++, n++) {
            if (temp[i] == pivot)
                res[n] = split;
            else {
                res[n] = temp[i];
            }
        }
        return res;
    }
}
