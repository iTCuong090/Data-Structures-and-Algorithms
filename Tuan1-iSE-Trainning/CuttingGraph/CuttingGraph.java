import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * BÀI TẬP: CUTTING A GRAPH (VNOJ Educational DSU)
 * -----------------------------------------------------------------------------
 * ĐỀ BÀI:
 * Cho đồ thị vô hướng gồm n đỉnh và m cạnh. Có k truy vấn gồm 2 loại:
 *   - cut u v : Xóa cạnh u-v khỏi đồ thị.
 *   - ask u v : Hỏi 2 đỉnh u và v có thuộc cùng một thành phần liên thông hay không.
 * 
 * Đề bài đảm bảo mọi cạnh ban đầu đều xuất hiện trong truy vấn cut đúng 1 lần.
 * Sau khi thực hiện toàn bộ truy vấn, đồ thị không còn cạnh nào.
 * 
 * THUẬT TOÁN QUAY NGƯỢC THỜI GIAN (TIME REVERSAL DSU):
 * 1. Vì các cạnh chỉ bị XÓA (cut), việc xử lý theo chiều xuôi rất khó.
 * 2. Ta quay ngược thời gian từ truy vấn k-1 về truy vấn 0:
 *    - Trạng thái ban đầu sau k truy vấn: Đồ thị không còn cạnh nào (n đỉnh cô lập).
 *    - Khi gặp truy vấn 'cut u v' theo chiều ngược: Tương đương THÊM CẠNH (u, v) -> Dùng DSU union(u, v).
 *    - Khi gặp truy vấn 'ask u v' theo chiều ngược: Trả lời xem find(u) == find(v) hay không -> Lưu kết quả.
 * 3. Đảo ngược các kết quả của 'ask' để in ra theo đúng thứ tự ban đầu.
 * -----------------------------------------------------------------------------
 */
public class CuttingGraph {

    // Mảng DSU quản lý cha của từng đỉnh
    private static int[] parent;

    /**
     * Hàm tìm Gốc đại diện trong DSU + Nén đường đi (Path Compression)
     */
    private static int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);
    }

    /**
     * Thao tác gộp 2 tập hợp chứa u và v
     */
    private static void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);
        if (rootU != rootV) {
            parent[rootV] = rootU;
        }
    }

    /**
     * Lớp đại diện cho một truy vấn (cut hoặc ask)
     */
    private static class Query {
        String type; // "cut" hoặc "ask"
        int u;
        int v;

        Query(String type, int u, int v) {
            this.type = type;
            this.u = u;
            this.v = v;
        }
    }

    public static void main(String[] args) throws IOException {
        // Sử dụng Fast I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = getNextTokenizer(br);

        if (st == null) return;

        int n = parseIntClean(st.nextToken()); // Số đỉnh
        int m = parseIntClean(st.nextToken()); // Số cạnh ban đầu
        int k = parseIntClean(st.nextToken()); // Số truy vấn

        // Đọc m cạnh ban đầu (Đề bài đảm bảo tất cả m cạnh đều sẽ bị cut)
        for (int i = 0; i < m; i++) {
            st = getNextTokenizer(br);
            // Đọc bỏ qua các cạnh ban đầu vì chúng sẽ bị cut hết
            parseIntClean(st.nextToken());
            parseIntClean(st.nextToken());
        }

        // Đọc k truy vấn
        Query[] queries = new Query[k];
        for (int i = 0; i < k; i++) {
            st = getNextTokenizer(br);
            String type = st.nextToken();
            int u = parseIntClean(st.nextToken());
            int v = parseIntClean(st.nextToken());
            queries[i] = new Query(type, u, v);
        }

        // ---------------------------------------------------------------------
        // BƯỚC 1: KHỞI TẠO DSU BAN ĐẦU (N ĐỈNH CÔ LẬP)
        // ---------------------------------------------------------------------
        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        // Danh sách lưu kết quả các truy vấn 'ask'
        ArrayList<String> answers = new ArrayList<>();

        // ---------------------------------------------------------------------
        // BƯỚC 2: DUYỆT NGƯỢC TỪ TRUY VẤN K-1 VỀ TRUY VẤN 0
        // ---------------------------------------------------------------------
        for (int i = k - 1; i >= 0; i--) {
            Query q = queries[i];
            if ("cut".equals(q.type)) {
                // Quá trình ngược: 'cut' biến thành THÊM CẠNH u-v vào đồ thị
                union(q.u, q.v);
            } else if ("ask".equals(q.type)) {
                // Kiểm tra 2 đỉnh u và v có thuộc cùng thành phần liên thông không
                if (find(q.u) == find(q.v)) {
                    answers.add("YES");
                } else {
                    answers.add("NO");
                }
            }
        }

        // ---------------------------------------------------------------------
        // BƯỚC 3: ĐẢO NGƯỢC KẾT QUẢ VÀ IN RA THEO THỨ TỰ BAN ĐẦU
        // ---------------------------------------------------------------------
        Collections.reverse(answers);

        PrintWriter pw = new PrintWriter(System.out);
        for (String ans : answers) {
            pw.println(ans);
        }
        pw.flush();
    }

    /**
     * Hàm đọc Token tiếp theo, bỏ qua các dòng trống
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
