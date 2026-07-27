import java.util.ArrayList;
import java.util.List;

public class Quicksort2Sorting {

    public static List<Integer> doMyQuickSort(List<Integer> numList) {
        if (numList.size() <= 1) {
            return numList;
        }

        int myPivot = numList.get(0);
        List<Integer> leftSide = new ArrayList<>();
        List<Integer> rightSide = new ArrayList<>();

        for (int i = 1; i < numList.size(); i++) {
            int num = numList.get(i);
            if (num < myPivot) {
                leftSide.add(num);
            } else {
                rightSide.add(num);
            }
        }

        List<Integer> leftSorted = doMyQuickSort(leftSide);
        List<Integer> rightSorted = doMyQuickSort(rightSide);

        List<Integer> mergedList = new ArrayList<>();
        for (int x : leftSorted) {
            mergedList.add(x);
        }
        mergedList.add(myPivot);
        for (int x : rightSorted) {
            mergedList.add(x);
        }

        for (int i = 0; i < mergedList.size(); i++) {
            System.out.print(mergedList.get(i));
            if (i < mergedList.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        return mergedList;
    }

    public static void main(String[] args) {
        String testInput1 = """
                7
                5 8 1 3 7 9 2
                """;

        String expectedOutput1 = """
                2 3
                1 2 3
                7 8 9
                1 2 3 5 7 8 9
                """;

        ProblemIO.test("Sample HackerRank Test", testInput1, expectedOutput1, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            List<Integer> arr = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                arr.add(io.nextInt());
            }
            doMyQuickSort(arr);
            io.flush();
        });
    }
}
