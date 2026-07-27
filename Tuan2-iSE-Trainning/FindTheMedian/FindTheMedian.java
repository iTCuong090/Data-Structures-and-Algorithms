import java.util.Scanner;

public class FindTheMedian {

    public static void main(String[] args) {
        String input1 = """
                7
                0 1 2 4 6 5 3
                """;

        String expectedOutput1 = """
                3
                """;

        ProblemIO.test("Test", input1, expectedOutput1, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            int[] a = io.readIntArray(n);

            int medianIndex = n / 2;
            int median = quickSelect(a, 0, n - 1, medianIndex);

            io.println(median);
            io.flush();
        });
    }

    private static int quickSelect(int[] a, int l, int r, int m) {
        if (l == r) {
            return a[l];
        }

        int p = partition(a, l, r);

        if (p == m) {
            return a[p];
        } else if (p > m) {
            return quickSelect(a, l, p - 1, m);
        } else {
            return quickSelect(a, p + 1, r, m);
        }
    }

    private static int partition(int[] a, int l, int r) {
        int pivot = a[r];
        int i = l;

        for (int j = l; j < r; j++) {
            if (a[j] <= pivot) {
                swap(a, i, j);
                i++;
            }
        }

        swap(a, i, r);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
