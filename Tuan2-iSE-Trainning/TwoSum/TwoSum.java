import java.util.*;

public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }

    public static void solve(ProblemIO io) {
        String nToken = io.next();
        if (nToken == null) return;
        int n = Integer.parseInt(nToken);
        int target = io.nextInt();
        int[] nums = io.readIntArray(n);

        int[] result = twoSum(nums, target);
        if (result.length == 2) {
            io.println(result[0] + " " + result[1]);
        } else {
            io.println("-1");
        }
        io.flush();
    }

    public static void main(String[] args) {
        String sampleInput1 = """
                4 9
                2 7 11 15
                """;

        String expectedOutput1 = """
                0 1
                """;

        String sampleInput2 = """
                3 6
                3 2 4
                """;

        String expectedOutput2 = """
                1 2
                """;

        String sampleInput3 = """
                2 6
                3 3
                """;

        String expectedOutput3 = """
                0 1
                """;

        ProblemIO.test("Test 1: Standard sample (target = 9)", sampleInput1, expectedOutput1, () -> {
            ProblemIO io = new ProblemIO();
            solve(io);
        });

        ProblemIO.test("Test 2: Target = 6", sampleInput2, expectedOutput2, () -> {
            ProblemIO io = new ProblemIO();
            solve(io);
        });

        ProblemIO.test("Test 3: Duplicate values (target = 6)", sampleInput3, expectedOutput3, () -> {
            ProblemIO io = new ProblemIO();
            solve(io);
        });
    }
}
