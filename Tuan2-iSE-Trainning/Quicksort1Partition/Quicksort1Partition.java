import java.util.ArrayList;
import java.util.List;

public class Quicksort1Partition {

    public static int[] partition(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        int pivot = arr[0];
        List<Integer> left = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int val : arr) {
            if (val < pivot) {
                left.add(val);
            } else if (val == pivot) {
                equal.add(val);
            } else {
                right.add(val);
            }
        }

        int[] result = new int[arr.length];
        int idx = 0;

        for (int val : left) {
            result[idx++] = val;
        }
        for (int val : equal) {
            result[idx++] = val;
        }
        for (int val : right) {
            result[idx++] = val;
        }

        return result;
    }

    public static void main(String[] args) {
        String input1 = """
                5
                4 5 3 7 2
                """;

        String expectedOutput1 = """
                3 2 4 5 7
                """;

        ProblemIO.test("Test", input1, expectedOutput1, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            int[] arr = io.readIntArray(n);

            int[] result = partition(arr);

            io.printArray(result);
            io.flush();
        });
    }
}

