import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;

public class ProblemIO implements AutoCloseable {

    private final BufferedReader reader;
    private StringTokenizer tokenizer;
    private final PrintWriter writer;

    public ProblemIO() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    }

    public ProblemIO(String inputSuperString) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputSuperString.getBytes(StandardCharsets.UTF_8));
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    }

    public ProblemIO(InputStream in, OutputStream out) {
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)));
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                String line = reader.readLine();
                if (line == null) return null;
                tokenizer = new StringTokenizer(line);
            } catch (IOException e) {
                throw new RuntimeException("Data read error: " + e.getMessage(), e);
            }
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        String token = next();
        if (token == null) throw new NoSuchElementException("End of input data (EOF)!");
        return Integer.parseInt(cleanNumberString(token));
    }

    public long nextLong() {
        String token = next();
        if (token == null) throw new NoSuchElementException("End of input data (EOF)!");
        return Long.parseLong(cleanNumberString(token));
    }

    public double nextDouble() {
        String token = next();
        if (token == null) throw new NoSuchElementException("End of input data (EOF)!");
        return Double.parseDouble(token);
    }

    public String nextLine() {
        tokenizer = null;
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Line read error: " + e.getMessage(), e);
        }
    }

    public int[] readIntArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nextInt();
        }
        return arr;
    }

    public long[] readLongArray(int n) {
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nextLong();
        }
        return arr;
    }

    public double[] readDoubleArray(int n) {
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nextDouble();
        }
        return arr;
    }

    public int[][] readIntMatrix(int rows, int cols) {
        int[][] mat = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j] = nextInt();
            }
        }
        return mat;
    }

    @SuppressWarnings("unchecked")
    public List<Integer>[] readGraph(int numNodes, int numEdges, boolean is1BasedIndex, boolean isDirected) {
        List<Integer>[] adj = new ArrayList[numNodes + (is1BasedIndex ? 1 : 0)];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 0; i < numEdges; i++) {
            int u = nextInt();
            int v = nextInt();
            adj[u].add(v);
            if (!isDirected) {
                adj[v].add(u);
            }
        }
        return adj;
    }

    public void print(Object obj) {
        writer.print(obj);
    }

    public void println(Object obj) {
        writer.println(obj);
    }

    public void println() {
        writer.println();
    }

    public void printf(String format, Object... args) {
        writer.printf(format, args);
    }

    public void printArray(int[] arr) {
        printArray(arr, " ");
    }

    public void printArray(int[] arr, String delimiter) {
        for (int i = 0; i < arr.length; i++) {
            writer.print(arr[i]);
            if (i < arr.length - 1) writer.print(delimiter);
        }
        writer.println();
    }

    public void printArray(long[] arr) {
        printArray(arr, " ");
    }

    public void printArray(long[] arr, String delimiter) {
        for (int i = 0; i < arr.length; i++) {
            writer.print(arr[i]);
            if (i < arr.length - 1) writer.print(delimiter);
        }
        writer.println();
    }

    public void printMatrix(int[][] mat) {
        for (int[] row : mat) {
            printArray(row, " ");
        }
    }

    @Override
    public void close() {
        writer.flush();
    }

    public void flush() {
        writer.flush();
    }

    public static void run(String inputSuperString, Consumer<ProblemIO> solver) {
        System.out.println("==================================================");
        System.out.println(">>> INPUT (SUPER STRING):");
        System.out.println(inputSuperString.trim());
        System.out.println("--------------------------------------------------");
        System.out.println(">>> OUTPUT:");
        
        long startTime = System.nanoTime();
        try (ProblemIO io = new ProblemIO(inputSuperString)) {
            solver.accept(io);
        }
        long endTime = System.nanoTime();
        
        double durationMs = (endTime - startTime) / 1e6;
        System.out.println("--------------------------------------------------");
        System.out.printf(">>> EXECUTION TIME: %.2f ms\n", durationMs);
        System.out.println("==================================================\n");
    }

    public static void runSystemIO(String inputSuperString, Runnable solverMain) {
        test("LOCAL TEST", inputSuperString, null, solverMain);
    }

    public static void test(String testName, String inputSuperString, String expectedOutput, Runnable solverMain) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream testIn = new ByteArrayInputStream(inputSuperString.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(capturedOut);

        System.setIn(testIn);
        System.setOut(testOut);

        long startTime = System.nanoTime();
        Throwable error = null;
        try {
            solverMain.run();
        } catch (Throwable t) {
            error = t;
        } finally {
            testOut.flush();
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1e6;

        String actualOutput = capturedOut.toString().trim().replace("\r\n", "\n");

        System.out.println("==================================================");
        System.out.println(" [TEST CASE]: " + testName);
        System.out.println("--------------------------------------------------");
        System.out.println("--- INPUT ---");
        System.out.println(inputSuperString.trim());
        System.out.println("--- ACTUAL OUTPUT ---");
        System.out.println(actualOutput.isEmpty() ? "(No output)" : actualOutput);

        if (error != null) {
            System.out.println("--------------------------------------------------");
            System.out.println(" STATUS: [ERROR - EXCEPTION / RUNTIME FAILURE]");
            error.printStackTrace(System.out);
        } else if (expectedOutput != null) {
            System.out.println("--------------------------------------------------");
            String expTrimmed = expectedOutput.trim().replace("\r\n", "\n");
            if (actualOutput.equals(expTrimmed)) {
                System.out.println(" STATUS: [PASSED] (Matched Expected Output 100%)");
            } else {
                System.out.println(" STATUS: [FAILED] (Output differs from Expected Output)");
                System.out.println("--- EXPECTED OUTPUT ---");
                System.out.println(expTrimmed);
            }
        }
        System.out.println("--------------------------------------------------");
        System.out.printf(" EXECUTION TIME: %.2f ms\n", durationMs);
        System.out.println("==================================================\n");
    }

    private static String cleanNumberString(String s) {
        return s.replaceAll("[^0-9-]", "");
    }

    public static void main(String[] args) {
        System.out.println("=== PROBLEM IO DEMO WITH SUPER STRING ===\n");

        String input1 = """
                5
                10 25 -5 40 30
                """;
        
        System.out.println("---> EXAMPLE 1: Read array and sum");
        ProblemIO.run(input1, io -> {
            int n = io.nextInt();
            int[] arr = io.readIntArray(n);
            
            long sum = 0;
            for (int x : arr) sum += x;
            
            io.println("Read array: ");
            io.printArray(arr);
            io.println("Sum = " + sum);
        });

        String input2 = """
                4
                3 1 4 2
                """;
        
        String expectedOutput2 = """
                Sorted: 1 2 3 4
                """;

        System.out.println("---> EXAMPLE 2: Test Sort algorithm with Expected Output");
        ProblemIO.test("Test QuickSort", input2, expectedOutput2, () -> {
            Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            
            Arrays.sort(a);
            
            System.out.print("Sorted: ");
            for (int i = 0; i < n; i++) {
                System.out.print(a[i] + (i == n - 1 ? "" : " "));
            }
            System.out.println();
        });
    }
}
