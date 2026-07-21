import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * BÀI TẬP: BOSSES (VNOJ Educational DSU)
 * -----------------------------------------------------------------------------
 * ĐỀ BÀI:
 * Rừng gồm n đỉnh. Ban đầu mỗi đỉnh là 1 gốc độc lập (khoảng cách tới gốc = 0).
 * Có 2 loại truy vấn:
 *   - 1 a b : Cho gốc a trở thành con của gốc b (a và b đảm bảo đều đang là gốc).
 *   - 2 c   : In khoảng cách từ node c đến gốc hiện tại của cây chứa c.
 * 
 * THUẬT TOÁN DSU TÍNH KHOẢNG CÁCH (WEIGHTED DSU / DSU WITH DISTANCE):
 * 1. Mảng parent[i]: Lưu nút cha của i.
 * 2. Mảng dist[i]: Lưu khoảng cách từ nút i tới nút cha parent[i].
 * 3. Thao tác find(i) kết hợp Nén đường đi & Cộng dồn khoảng cách:
 *    - Đệ quy nén nút cha p = parent[i] về Gốc.
 *    - Cập nhật dist[i] += dist[p].
 *    - Gán parent[i] = root.
 * 4. Thao tác 1 a b: Đề bài đảm bảo a và b đang là gốc:
 *    - Gán parent[a] = b.
 *    - Gán dist[a] = 1.
 * 5. Thao tác 2 c:
 *    - Gọi find(c) để vừa nén đường đi vừa cộng dồn khoảng cách từ c về gốc.
 *    - In ra dist[c].
 * -----------------------------------------------------------------------------
 */
public class Bosses {

    private static int[] parent;
    private static int[] dist;

    /**
     * Hàm tìm Gốc đại diện + Nén đường đi + Cộng dồn khoảng cách về Gốc
     */
    private static int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        int p = parent[i];
        int root = find(p); // Đệ quy đưa p về Gốc trước

        // Sau khi p đã trỏ trực tiếp về Gốc, dist[p] chính là khoảng cách từ p về Gốc
        dist[i] += dist[p];
        parent[i] = root; // Nén đường đi

        return root;
    }

    public static void main(String[] args) throws IOException {
        // Fast I/O cho N, M <= 300,000
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = getNextTokenizer(br);

        if (st == null) return;

        int n = parseIntClean(st.nextToken()); // Số đỉnh
        int m = parseIntClean(st.nextToken()); // Số truy vấn

        parent = new int[n + 1];
        dist = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            dist[i] = 0;
        }

        PrintWriter pw = new PrintWriter(System.out);

        for (int k = 0; k < m; k++) {
            st = getNextTokenizer(br);
            int type = parseIntClean(st.nextToken());

            if (type == 1) {
                int a = parseIntClean(st.nextToken());
                int b = parseIntClean(st.nextToken());

                // Đề bài đảm bảo a và b đều đang là Gốc
                parent[a] = b;
                dist[a] = 1; // a thành con trực tiếp của b -> khoảng cách = 1

            } else if (type == 2) {
                int c = parseIntClean(st.nextToken());

                // Gọi find(c) để cập nhật khoảng cách thực tế từ c về Gốc
                find(c);
                pw.println(dist[c]);
            }
        }

        pw.flush();
    }

    /**
     * Hàm đọc Token tiếp theo, bỏ qua dòng trống
     */
    private static StringTokenizer getNextTokenizer(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                return new StringTokenizer(line);
            }
        }
        return null;
    }

    /**
     * Hàm parse chuỗi thành int an toàn, loại bỏ ký tự lạ (BOM)
     */
    private static int parseIntClean(String str) {
        return Integer.parseInt(str.replaceAll("[^0-9-]", ""));
    }
}
