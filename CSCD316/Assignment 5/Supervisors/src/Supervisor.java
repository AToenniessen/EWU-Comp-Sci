import java.io.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Supervisor {
    private static int[][] Supervisors;
    private static int[][] Employees;

    public static void main(String... args) throws IOException {
        String f;
        if (args.length == 1) {
            f = args[0];
        } else
            f = "supervisors.in";
        BufferedReader fin = openFile(f);

        int cases = Integer.parseInt(fin.readLine());
        for (int c = 1; c <= cases; c++) {
            Supervisors = new int[7][7];
            Employees = new int[7][7];
            int[][] permutations = new int[5040][];
            popPerm(permutations);
            readFile(fin, Supervisors, 7);
            readFile(fin, Employees, 7);
            fin.readLine();
            double lowest = 8.0;
            ArrayList<int[]> bestPerm = new ArrayList<>();
            double score;
            for (int[] a : permutations) {
                score = findScore(a);
                if (score < lowest) {
                    lowest = score;
                    bestPerm = new ArrayList<>();
                    bestPerm.add(a);
                } else if (score == lowest)
                    bestPerm.add(a);
            }
            System.out.printf("Data Set" + c + ", Best average difference: %.6f\n", lowest);
            int i = 1;
            for(int[] n : bestPerm){
                System.out.println("Best pairing " + i);
                i++;
                for(int p = 0; p < 7; p++){
                    System.out.println("Supervisor "+ (p+1) +" with Employee "+ n[p]);
                }
            }
        }

    }
    private static void popPerm(int[][] ara){
        int[] perm = {1,2,3,4,5,6,7};
        ara[0] = perm;
        for(int i = 1; i < 5040; i++){
            int[] nextPerm = nextPerm(perm);
            ara[i] = nextPerm;
            perm = nextPerm;
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
    private static void readFile(BufferedReader in, int[][] data, int q) throws IOException {
        int n = 0;
        while (n < q) {
            data[n] = Stream.of(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            n++;
        }
    }
    private static BufferedReader openFile(String f) {
        try {
            return new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        return null;
    }
    private static double findScore(int[] p) {
        int dif = 0;
        ArrayList<Integer> employeePermutation = new ArrayList<>(p.length);
        for (int aP : p) employeePermutation.add(aP);
        for (int index = 0; index < 7; index++) {
            int[] spref = Supervisors[index];
            int[] ePref = Employees[index];
            for (int j = 0; j < 7; j++) {   //looks through supervisors preferences and compares to the value (employee) in the permutation passed in that they were assigned
                if (spref[j] == p[index]) {
                    dif += j;
                    break;
                }
            }
            int n = employeePermutation.indexOf(index+1) + 1;       //index represents supervisor that employee is matched to for each permutation passed in
            for (int j = 0; j < 7; j++) {   //looks through employee preferences and compares them to the supervisor (n) they are matched up to
                if (ePref[j] == n) {
                    dif += j;       //j represents how far off the employee is from the supervisor they wanted
                    break;
                }
            }
        }
        return dif / 14.0;
    }
}
