import java.util.*;

public class MinStock {

    static class Stock implements Comparable<Stock> {
        String name;
        long price;
        int version;

        public Stock(String name, long price, int version) {
            this.name = name;
            this.price = price;
            this.version = version;
        }

        @Override
        public int compareTo(Stock o) {
            if (this.price != o.price) {
                return Long.compare(this.price, o.price);
            }
            return this.name.compareTo(o.name);
        }
    }

    public static void solve(ProblemIO io) {
        String nToken = io.next();
        if (nToken == null) return;
        int n = Integer.parseInt(nToken);

        PriorityQueue<Stock> pq = new PriorityQueue<>();
        Map<String, Integer> versionMap = new HashMap<>();

        for (int day = 1; day <= n; day++) {
            int type = io.nextInt();
            if (type == 1) {
                String x = io.next();
                long y = io.nextLong();

                int ver = versionMap.getOrDefault(x, 0) + 1;
                versionMap.put(x, ver);
                pq.add(new Stock(x, y, ver));

            } else if (type == 2) {
                String x = io.next();
                long z = io.nextLong();

                int ver = versionMap.get(x) + 1;
                versionMap.put(x, ver);
                pq.add(new Stock(x, z, ver));

            } else if (type == 3) {
                String buyCmd = io.next(); // Reads "BUY"

                while (!pq.isEmpty()) {
                    Stock top = pq.poll();

                    Integer currentVer = versionMap.get(top.name);
                    if (currentVer != null && currentVer == top.version) {
                        io.println(top.name + " " + day);
                        versionMap.remove(top.name);
                        break;
                    }
                }
            }
        }
        io.flush();
    }

    public static void main(String[] args) {
        String sampleInput = """
                7
                1 ABC 32
                1 XDC 54
                3 BUY
                1 XCD 32
                1 ABC 12
                2 XDC 10
                3 BUY
                """;

        String expectedOutput = """
                ABC 3
                XDC 7
                """;

        ProblemIO.test("Sample Test Case", sampleInput, expectedOutput, () -> {
            ProblemIO io = new ProblemIO();
            solve(io);
        });
    }
}
