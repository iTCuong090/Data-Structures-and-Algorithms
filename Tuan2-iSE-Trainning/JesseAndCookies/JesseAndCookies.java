import java.util.*;

public class JesseAndCookies {

    public static int cookies(int k, List<Integer> A) {
        PriorityQueue<Long> pq = new PriorityQueue<>();
        for (int val : A) {
            pq.add((long) val);
        }

        int ops = 0;
        while (!pq.isEmpty() && pq.peek() < k) {
            if (pq.size() < 2) {
                return -1;
            }

            long first = pq.poll();
            long second = pq.poll();

            long combined = first + 2 * second;
            pq.add(combined);
            ops++;
        }

        return ops;
    }

    public static void main(String[] args) {
        String input1 = """
                6 7
                1 2 3 9 10 12
                """;
        String expectedOutput1 = """
                2
                """;

        String input2 = """
                6 9
                2 7 3 6 4 6
                """;
        String expectedOutput2 = """
                4
                """;

        String input3 = """
                3 20
                1 1 1
                """;
        String expectedOutput3 = """
                -1
                """;

        ProblemIO.test("Test 1: Sample Input 1 (k = 7)", input1, expectedOutput1, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            int k = io.nextInt();
            List<Integer> A = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                A.add(io.nextInt());
            }
            io.println(cookies(k, A));
            io.flush();
        });

        ProblemIO.test("Test 2: Sample Input 2 (k = 9)", input2, expectedOutput2, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            int k = io.nextInt();
            List<Integer> A = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                A.add(io.nextInt());
            }
            io.println(cookies(k, A));
            io.flush();
        });

        ProblemIO.test("Test 3: Impossible case (return -1)", input3, expectedOutput3, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            int k = io.nextInt();
            List<Integer> A = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                A.add(io.nextInt());
            }
            io.println(cookies(k, A));
            io.flush();
        });
    }
}
