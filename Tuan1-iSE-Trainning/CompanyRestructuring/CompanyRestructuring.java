import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * BÀI TẬP: TÁI CẤU TRÚC CÔNG TY (VNOJ Educational DSU)
 * -----------------------------------------------------------------------------
 * ĐỀ BÀI:
 * n nhân viên (1 đến n), ban đầu mỗi người 1 phòng ban.
 * q truy vấn thuộc 3 loại:
 *   - 1 x y : Gộp phòng ban chứa x và phòng ban chứa y.
 *   - 2 x y : Gộp TẤT CẢ các phòng ban chứa các nhân viên từ x đến y (x, x+1, ..., y).
 *   - 3 x y : Hỏi nhân viên x và y có đang làm cùng phòng ban hay không (in YES/NO).
 * 
 * PHẢN BIỆN & THUẬT TOÁN KẾT HỢP DSU KÉP (DUAL DSU):
 * 1. Vì sao có truy vấn Type 2?
 *    - Nếu sáp nhập đoạn [x, y] bằng cách duyệt lặp từng cặp (i, i+1), thời gian cho 1 truy vấn
 *      có thể lên tới O(N) -> Tổng thời gian O(Q * N) sẽ bị TLE!
 *    - Type 2 được thiết kế để kiểm tra kỹ thuật "DSU Nhảy Đoạn" (Skip DSU).
 * 
 * 2. Giải pháp DSU Kép:
 *    - DSU 1 (`parent[i]`): Quản lý các phòng ban (dùng cho Type 1 và Type 3).
 *    - DSU 2 (`next_unmerged[i]`): Quản lý các phần tử ĐÃ ĐƯỢC NỐI LIỀN NHAU trong đoạn liên tiếp.
 *      `next_unmerged[i]` lưu vị trí k KẾ TIẾP (k > i) mà ranh giới (k-1, k) CHƯA được sáp nhập.
 * 
 * 3. Phân tích độ phức tạp khấu trừ (Amortized Complexity):
 *    - Mỗi ranh giới (i, i+1) chỉ bị gộp ĐÚNG 1 LẦN DUY NHẤT trong toàn bộ chương trình.
 *    - Sau khi ranh giới (i, i+1) được gộp, `next_unmerged` sẽ nhảy cóc qua nó ở tất cả các truy vấn Type 2 sau.
 *    - Do đó, vòng lặp gộp đoạn chỉ chạy tối đa N-1 lần CHO TOÀN BỘ CHƯƠNG TRÌNH!
 *    - Tổng độ phức tạp: O((N + Q) * alpha(N)) ~ O(1) cho mỗi truy vấn.
 * -----------------------------------------------------------------------------
 */
public class CompanyRestructuring {

    // DSU 1: Quản lý phòng ban (Type 1 và Type 3)
    private static int[] parent;

    // DSU 2: Quản lý con trỏ nhảy đoạn liên tiếp (Type 2)
    private static int[] nextUnmerged;

    /**
     * Tìm phòng ban đại diện cho x trong DSU 1 (Path Compression)
     */
    private static int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);
    }

    /**
     * Gộp 2 phòng ban chứa u và v trong DSU 1
     */
    private static void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);
        if (rootU != rootV) {
            parent[rootV] = rootU;
        }
    }

    /**
     * Tìm vị trí kế tiếp chưa được sáp nhập liền kề trong DSU 2 (Path Compression)
     */
    private static int findNext(int i) {
        if (nextUnmerged[i] == i) {
            return i;
        }
        return nextUnmerged[i] = findNext(nextUnmerged[i]);
    }

    public static void main(String[] args) throws IOException {
        // Fast I/O cho N <= 200,000, Q <= 500,000
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = getNextTokenizer(br);

        if (st == null) return;

        int n = parseIntClean(st.nextToken()); // Số nhân viên
        int q = parseIntClean(st.nextToken()); // Số truy vấn

        // Khởi tạo DSU 1 và DSU 2
        parent = new int[n + 1];
        nextUnmerged = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            nextUnmerged[i] = i;
        }

        PrintWriter pw = new PrintWriter(System.out);

        for (int k = 0; k < q; k++) {
            st = getNextTokenizer(br);
            int type = parseIntClean(st.nextToken());
            int x = parseIntClean(st.nextToken());
            int y = parseIntClean(st.nextToken());

            if (type == 1) {
                // Gộp phòng ban chứa x và y
                union(x, y);

            } else if (type == 2) {
                // Gộp tất cả phòng ban chứa nhân viên từ x đến y
                int curr = findNext(x);
                while (curr < y) {
                    // Gộp phòng ban của curr và curr + 1 trong DSU 1
                    union(curr, curr + 1);

                    // Đánh dấu ranh giới (curr, curr + 1) đã được nối liền
                    // nextUnmerged[curr] nhảy tới vị trí chưa gộp tiếp theo >= curr + 1
                    nextUnmerged[curr] = findNext(curr + 1);

                    // Tiến tới vị trí chưa gộp tiếp theo
                    curr = nextUnmerged[curr];
                }

            } else if (type == 3) {
                // Kiểm tra x và y có cùng phòng ban hay không
                if (find(x) == find(y)) {
                    pw.println("YES");
                } else {
                    pw.println("NO");
                }
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
