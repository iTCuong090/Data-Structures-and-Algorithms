import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * BÀI TẬP: MỌI NGƯỜI ĐANG RỜI ĐI (VNOJ Educational DSU)
 * -----------------------------------------------------------------------------
 * ĐỀ BÀI:
 * Có n người đứng ở các vị trí 1 đến n.
 * Hai loại truy vấn:
 *   - - x : Người ở vị trí x rời đi.
 *   - ? x : Tìm vị trí gần nhất >= x mà vẫn còn người đứng. Nếu không có, in -1.
 * 
 * PHẢN BIỆN & THUẬT TOÁN TỐI ƯU (DSU + PATH COMPRESSION):
 * 1. Không cần danh sách liên kết kép (con trỏ bên trái) vì truy vấn chỉ hỏi >= x (bên phải).
 * 2. Nếu chỉ dùng danh sách liên kết đơn thuần, khi có chuỗi người rời đi liên tiếp x, x+1, x+2...
 *    truy vấn ? x sẽ mất O(N) để nhảy qua từng nút -> Tổng độ phức tạp O(M * N) bị TLE!
 * 3. Giải pháp DSU + Nén đường đi (Path Compression):
 *    - parent[i] lưu vị trí CÒN NGƯỜI ĐỨNG nhỏ nhất >= i.
 *    - Khởi tạo: parent[i] = i cho i = 1 đến n + 1 (n + 1 là vị trí phụ / sentinel).
 *    - Khi - x: Cho parent[x] = find(x + 1) (gộp vị trí x vào vị trí kế tiếp).
 *    - Khi ? x: Trả về find(x). Nếu find(x) == n + 1 thì in -1, ngược lại in find(x).
 *    - Độ phức tạp mỗi truy vấn: O(alpha(N)) ~ O(1).
 * -----------------------------------------------------------------------------
 */
public class PeopleLeaving {

    private static int[] parent;

    /**
     * Hàm tìm vị trí còn người đứng nhỏ nhất >= i + Nén đường đi (Path Compression)
     */
    private static int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);
    }

    public static void main(String[] args) throws IOException {
        // Fast I/O cho N, M <= 10^6
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = getNextTokenizer(br);

        if (st == null) return;

        int n = parseIntClean(st.nextToken()); // Số người (1 đến n)
        int m = parseIntClean(st.nextToken()); // Số truy vấn

        // parent có kích thước n + 2 để chứa nút phụ n + 1 (sentinel)
        parent = new int[n + 2];
        for (int i = 1; i <= n + 1; i++) {
            parent[i] = i;
        }

        PrintWriter pw = new PrintWriter(System.out);

        for (int i = 0; i < m; i++) {
            st = getNextTokenizer(br);
            String type = st.nextToken();
            int x = parseIntClean(st.nextToken());

            if ("-".equals(type)) {
                // Người x rời đi -> gán đại diện của x là vị trí khả dụng kế tiếp >= x + 1
                parent[x] = find(x + 1);
            } else if ("?".equals(type)) {
                // Tìm vị trí khả dụng gần nhất >= x
                int nextAvailable = find(x);
                if (nextAvailable > n) {
                    pw.println(-1);
                } else {
                    pw.println(nextAvailable);
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
