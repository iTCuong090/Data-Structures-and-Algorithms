import java.util.Scanner;

public class QuicksortInPlace {

    public static void main(String[] args) {
        String input1 = """
                7
                1 3 9 8 2 7 5
                """;

        String expectedOutput1 = """
                1 3 2 5 9 7 8
                1 2 3 5 9 7 8
                1 2 3 5 7 8 9
                """;

        ProblemIO.test("Test", input1, expectedOutput1, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            int[] a = io.readIntArray(n);

            quickSort(a, 0, n - 1);
            io.flush();
        });
    }

    private static void quickSort(int[] a, int l, int r) {
        if (l >= r) return;

        int p = partition(a, l, r);
        printArray(a);

        quickSort(a, l, p - 1);
        quickSort(a, p + 1, r);
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

    private static void printArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            if (i < a.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
