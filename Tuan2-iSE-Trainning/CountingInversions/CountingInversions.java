import java.util.ArrayList;
import java.util.List;

public class CountingInversions {

    static class Res {
        List<Integer> list;
        long cnt;

        Res(List<Integer> list, long cnt) {
            this.list = list;
            this.cnt = cnt;
        }
    }

    public static Res count(List<Integer> a) {
        if (a.size() <= 1) return new Res(a, 0);

        int mid = a.size() / 2;
        List<Integer> l = new ArrayList<>(a.subList(0, mid));
        List<Integer> r = new ArrayList<>(a.subList(mid, a.size()));

        Res left = count(l);
        Res right = count(r);
        Res merged = merge(left.list, right.list);

        return new Res(merged.list, left.cnt + right.cnt + merged.cnt);
    }

    private static Res merge(List<Integer> l, List<Integer> r) {
        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;
        long cnt = 0;

        while (i < l.size() && j < r.size()) {
            if (l.get(i) <= r.get(j)) {
                res.add(l.get(i));
                i++;
            } else {
                res.add(r.get(j));
                cnt += (l.size() - i);
                j++;
            }
        }

        while (i < l.size()) {
            res.add(l.get(i++));
        }

        while (j < r.size()) {
            res.add(r.get(j++));
        }

        return new Res(res, cnt);
    }

    public static void main(String[] args) {
        String input1 = """
                2
                5
                1 1 1 2 2
                5
                2 1 3 1 2
                """;

        String expectedOutput1 = """
                0
                4
                """;

        ProblemIO.test("Test", input1, expectedOutput1, () -> {
            ProblemIO io = new ProblemIO();
            int d = io.nextInt();

            for (int tc = 0; tc < d; tc++) {
                int n = io.nextInt();
                List<Integer> a = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    a.add(io.nextInt());
                }

                Res ans = count(a);
                io.println(ans.cnt);
            }
            io.flush();
        });
    }
}
