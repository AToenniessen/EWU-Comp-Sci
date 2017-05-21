import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Created by Alex on 12/4/2016.
 */
public class Minesweeper {
    public static void main(String... ars) throws IOException {
        BufferedReader in = openFile("Input.txt");
        readFile(in);
    }

    private static void readFile(BufferedReader in) throws IOException {
        int i = 0;
        while (in.ready()) {
            i++;
            int[] xy = Stream.of(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int x = xy[0];
            int y = xy[1];
            if(x == 0 || y== 0)
                break;
            int[][] map = new int[x][y];
            for (int n = 0; n < x; n++) {
                String[] s = in.readLine().split("");
                for (int m = 0; m < y; m++) {
                    if (s[m].compareTo("*") == 0)
                        map[n][m] = -1;
                }
            }
            System.out.printf("Field #%d:\n", i);
            processMap(map, x, y);
        }
    }

    private static void processMap(int[][] m, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (m[i][j] == -1) {
                    if (i - 1 >= 0 && j - 1 >= 0 && m[i - 1][j - 1] != -1) {
                        m[i - 1][j - 1]++;
                    }
                    if (i - 1 >= 0 && m[i - 1][j] != -1) {
                        m[i - 1][j]++;
                    }
                    if (i - 1 >= 0 && j + 1 < col && m[i - 1][j + 1] != -1) {
                        m[i - 1][j + 1]++;
                    }
                    if (j + 1 < col && m[i][j + 1] != -1) {
                        m[i][j + 1]++;
                    }
                    if (i + 1 < row && j + 1 < col && m[i + 1][j + 1] != -1) {
                        m[i + 1][j + 1]++;
                    }
                    if (i + 1 < row && m[i + 1][j] != -1) {
                        m[i + 1][j]++;
                    }
                    if (i + 1 < row && j - 1 >= 0 && m[i + 1][j - 1] != -1) {
                        m[i + 1][j - 1]++;
                    }
                    if (j - 1 >= 0 && m[i][j - 1] != -1) {
                        m[i][j - 1]++;
                    }
                }
            }
        }
        printMap(m);
    }

    private static void printMap(int[][] m) {
        for (int[] i : m) {
            for (int n : i) {
                if (n == -1)
                    System.out.print("*");
                else
                    System.out.printf("%d", n);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static BufferedReader openFile(String f) {
        try {
            return new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        return null;
    }
}
