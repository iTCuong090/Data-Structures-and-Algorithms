import java.util.*;

public class DeleteNodeInBST {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else { // Found the key to delete!
            // Case 1: Leaf node (no children)
            if (root.left == null && root.right == null) {
                return null;
            }
            // Case 2: Node has 1 child
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            // Case 3: Node has 2 children (find min in right subtree)
            TreeNode minNode = root.right;
            while (minNode.left != null) {
                minNode = minNode.left;
            }
            root.val = minNode.val;
            root.right = deleteNode(root.right, minNode.val);
        }
        return root;
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

    private static String serialize(TreeNode root) {
        if (root == null) return "[]";
        List<String> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr != null) {
                res.add(String.valueOf(curr.val));
                queue.add(curr.left);
                queue.add(curr.right);
            } else {
                res.add("null");
            }
        }
        while (res.size() > 0 && res.get(res.size() - 1).equals("null")) {
            res.remove(res.size() - 1);
        }
        return res.toString();
    }

    public static void main(String[] args) {
        String input1 = """
                7
                5 3 6 2 4 null 7
                3
                """;
        String expectedOutput1 = """
                [5, 4, 6, 2, null, null, 7]
                """;

        String input2 = """
                7
                5 3 6 2 4 null 7
                0
                """;
        String expectedOutput2 = """
                [5, 3, 6, 2, 4, null, 7]
                """;

        String input3 = """
                0
                0
                """;
        String expectedOutput3 = """
                []
                """;

        ProblemIO.test("Test 1: Delete node with 2 children", input1, expectedOutput1, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            String[] tokens = new String[n];
            for (int i = 0; i < n; i++) {
                tokens[i] = io.next();
            }
            int key = io.nextInt();
            TreeNode root = buildTree(tokens);
            root = deleteNode(root, key);
            io.println(serialize(root));
            io.flush();
        });

        ProblemIO.test("Test 2: Key not found in tree", input2, expectedOutput2, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            String[] tokens = new String[n];
            for (int i = 0; i < n; i++) {
                tokens[i] = io.next();
            }
            int key = io.nextInt();
            TreeNode root = buildTree(tokens);
            root = deleteNode(root, key);
            io.println(serialize(root));
            io.flush();
        });

        ProblemIO.test("Test 3: Empty tree", input3, expectedOutput3, () -> {
            ProblemIO io = new ProblemIO();
            int n = io.nextInt();
            String[] tokens = new String[n];
            for (int i = 0; i < n; i++) {
                tokens[i] = io.next();
            }
            int key = io.nextInt();
            TreeNode root = buildTree(tokens);
            root = deleteNode(root, key);
            io.println(serialize(root));
            io.flush();
        });
    }
}
