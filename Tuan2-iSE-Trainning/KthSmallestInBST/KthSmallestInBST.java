import java.util.*;

public class KthSmallestInBST {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    private static int count = 0;
    private static int ans = -1;

    public static int kthSmallest(TreeNode root, int k) {
        count = 0;
        ans = -1;
        inorder(root, k);
        return ans;
    }

    private static void inorder(TreeNode root, int k) {
        if (root == null || count >= k) return;

        inorder(root.left, k);

        count++;
        if (count == k) {
            ans = root.val;
            return;
        }

        inorder(root.right, k);
    }

    private static TreeNode buildTree(String[] tokens) {
        if (tokens.length == 0 || tokens[0].equals("null")) return null;
        TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty() && i < tokens.length) {
            TreeNode curr = queue.poll();
            if (i < tokens.length) {
                if (!tokens[i].equals("null")) {
                    curr.left = new TreeNode(Integer.parseInt(tokens[i]));
                    queue.add(curr.left);
                }
                i++;
            }
            if (i < tokens.length) {
                if (!tokens[i].equals("null")) {
                    curr.right = new TreeNode(Integer.parseInt(tokens[i]));
                    queue.add(curr.right);
                }
                i++;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        String input1 = """
                5
                3 1 4 null 2
                1
                """;
        String expectedOutput1 = """
                1
                """;

        String input2 = """
                8
                5 3 6 2 4 null null 1
                3
                """;
        String expectedOutput2 = """
                3
                """;

        String input3 = """
                1
                10
                1
                """;
        String expectedOutput3 = """
                10
                """;

        ProblemIO.test("Test 1: Example 1 (k = 1)", input1, expectedOutput1, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            String[] tokens = new String[n];
            for (int i = 0; i < n; i++) {
                tokens[i] = io.next();
            }
            int k = io.nextInt();
            TreeNode root = buildTree(tokens);
            io.println(kthSmallest(root, k));
            io.flush();
        });

        ProblemIO.test("Test 2: Example 2 (k = 3)", input2, expectedOutput2, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            String[] tokens = new String[n];
            for (int i = 0; i < n; i++) {
                tokens[i] = io.next();
            }
            int k = io.nextInt();
            TreeNode root = buildTree(tokens);
            io.println(kthSmallest(root, k));
            io.flush();
        });

        ProblemIO.test("Test 3: Single node tree (k = 1)", input3, expectedOutput3, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            String[] tokens = new String[n];
            for (int i = 0; i < n; i++) {
                tokens[i] = io.next();
            }
            int k = io.nextInt();
            TreeNode root = buildTree(tokens);
            io.println(kthSmallest(root, k));
            io.flush();
        });
    }
}
