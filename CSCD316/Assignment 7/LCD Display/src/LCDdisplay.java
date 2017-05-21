import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Stream;

public class LCDdisplay {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("Input.txt"));
        while (in.ready()) {
            String[] input = in.readLine().split(" ");
            int s = Integer.parseInt(input[0]);
            int[] value = Stream.of(input[1].split("")).mapToInt(Integer::parseInt).toArray();
            Segment[] display = new Segment[value.length];
            for (int i = 0; i < value.length; i++) {
                display[i] = new Segment(setSegment(value[i]), s);
            }
            for (int y = 0; y < (s * 2) + 3; y++) {
                for (Segment n : display) {
                    System.out.print(n.toString(y) + " ");
                }
                System.out.println();
            }
        }
    }

    private static boolean[] setSegment(int n) {
        boolean[] segments = new boolean[7];
        segments[0] = n != 1 && n != 4;
        segments[1] = n != 5 && n != 6;
        segments[2] = n != 2;
        segments[3] = n != 1 && n != 4 && n != 7;
        segments[4] = n == 0 || n == 2 || n == 6 || n == 8;
        segments[5] = n != 1 && n != 2 && n != 3 && n != 7;
        segments[6] = n != 0 && n != 1 && n != 7;
        return segments;
    }
}
class Segment {
    private String[][] pixels;

    Segment(boolean[] segments, int width) {
        int segmentWidth = width + 2;
        int segmentHeight = (width * 2) + 3;
        pixels = new String[segmentHeight][segmentWidth];
        for (int y = 0; y < segmentHeight; y++) {
            for (int x = 0; x < segmentWidth; x++) {
                pixels[y][x] = " ";
            }
        }
        if (segments[0])
            for (int x = 1; x < segmentWidth - 1; x++)
                pixels[0][x] = "-";
        if (segments[1])
            for (int y = 1; y < segmentWidth - 1; y++)
                pixels[y][segmentWidth - 1] = "|";
        if (segments[2])
            for (int y = segmentWidth; y < segmentHeight - 1; y++)
                pixels[y][segmentWidth - 1] = "|";
        if (segments[3])
            for (int x = 1; x < segmentWidth - 1; x++)
                pixels[segmentHeight - 1][x] = "-";
        if (segments[4])
            for (int y = segmentWidth; y < segmentHeight - 1; y++)
                pixels[y][0] = "|";
        if (segments[5])
            for (int y = 1; y < segmentWidth - 1; y++)
                pixels[y][0] = "|";
        if (segments[6])
            for (int x = 1; x < segmentWidth - 1; x++)
                pixels[segmentWidth - 1][x] = "-";
    }

    String toString(int y) {
        String out = "";
        String [] row = pixels[y];
        for (String s : row)
            out += s;
        return out;
    }
}
