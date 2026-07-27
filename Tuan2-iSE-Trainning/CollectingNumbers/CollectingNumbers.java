public class CollectingNumbers {

    public static void main(String[] args) {
        String input1 = """
                5
                4 2 1 5 3
                """;

        String expectedOutput1 = """
                3
                """;

        ProblemIO.test("Test", input1, expectedOutput1, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            int[] pos = new int[n + 1];

            for (int i = 0; i < n; i++) {
                int val = io.nextInt();
                pos[val] = i;
            }

            int rounds = 1;
            for (int i = 2; i <= n; i++) {
                if (pos[i] < pos[i - 1]) {
                    rounds++;
                }
            }

            io.println(rounds);
            io.flush();
        });
    }
}
