/**
 * BÀI TẬP: NUMBER OF PROVINCES (LeetCode 547 / VNOJ Educational DSU)
 * -----------------------------------------------------------------------------
 * ĐỀ BÀI:
 * Có n thành phố. Ma trận isConnected kích thước n x n mô tả kết nối giữa các thành phố:
 * isConnected[i][j] = 1 nếu thành phố i và j nối trực tiếp với nhau, ngược lại = 0.
 * Một province (tỉnh) là một thành phần liên thông các thành phố.
 * Đếm tổng số province.
 * 
 * THUẬT TOÁN DSU VỚI PATH COMPRESSION:
 * 1. Khởi tạo DSU với n phần tử (0 đến n-1). Ban đầu số thành phần liên thông count = n.
 * 2. Duyệt qua nửa trên của ma trận đối xứng isConnected (với j > i):
 *    - Nếu isConnected[i][j] == 1:
 *      - Gọi union(i, j). Nếu i và j thuộc 2 tập hợp khác nhau, gộp 2 tập hợp lại và giảm count--.
 * 3. Trả về count.
 * 4. Độ phức tạp: O(N^2 * alpha(N)) ~ O(N^2) cho việc duyệt ma trận, cực kỳ nhanh.
 * -----------------------------------------------------------------------------
 */
public class NumberOfProvinces {

    public static class Solution {
        private int[] parent;

        private int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]);
        }

        public int findCircleNum(int[][] isConnected) {
            int n = isConnected.length;
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }

            int count = n; // Ban đầu có n thành phần liên thông độc lập

            // Duyệt nửa trên ma trận adjacency matrix (j > i)
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (isConnected[i][j] == 1) {
                        int rootI = find(i);
                        int rootJ = find(j);
                        if (rootI != rootJ) {
                            parent[rootJ] = rootI; // Gộp 2 thành phần liên thông
                            count--; // Giảm số lượng thành phần liên thông
                        }
                    }
                }
            }

            return count;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // Sample 1: [[1,1,0],[1,1,0],[0,0,1]]
        int[][] test1 = {
            {1, 1, 0},
            {1, 1, 0},
            {0, 0, 1}
        };
        System.out.println("Sample 1 Output: " + sol.findCircleNum(test1)); // Expected: 2

        // Sample 2: [[1,0,0],[0,1,0],[0,0,1]]
        int[][] test2 = {
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
        System.out.println("Sample 2 Output: " + sol.findCircleNum(test2)); // Expected: 3
    }
}
