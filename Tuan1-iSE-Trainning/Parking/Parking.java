import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * BÀI TẬP: PARKING (VNOJ Educational DSU)
 * -----------------------------------------------------------------------------
 * ĐỀ BÀI:
 * Có n chỗ đỗ xe xếp thành vòng tròn (từ 1 đến n).
 * Xe i muốn đỗ ở vị trí p_i. Nếu p_i đã có xe đỗ, xe tiếp tục đi theo chiều kim
 * đồng hồ đến chỗ trống đầu tiên và đỗ tại đó.
 * Xác định chỗ đỗ thực tế của từng xe.
 * 
 * THUẬT TOÁN DSU VÒNG TRÒN (CIRCULAR DSU + PATH COMPRESSION):
 * 1. Mảng parent[i] đại diện cho chỗ đỗ KHẢ DỤNG tiếp theo theo chiều kim đồng hồ >= i.
 * 2. Khởi tạo: parent[i] = i cho mọi i từ 1 đến n.
 * 3. Khi xe muốn đỗ ở p_i:
 *    - Gọi actualSpot = find(p_i) để tìm chỗ đỗ trống thực tế.
 *    - Chỗ đỗ tiếp theo theo chiều kim đồng hồ là: nextSpot = (actualSpot % n) + 1.
 *    - Cập nhật parent[actualSpot] = find(nextSpot) (nối chỗ vừa đỗ sang chỗ trống tiếp theo).
 *    - In ra actualSpot.
 * 4. Độ phức tạp: O(alpha(N)) cho mỗi xe -> Tổng thời gian O(N * alpha(N)).
 * -----------------------------------------------------------------------------
 */
public class Parking {

    private static int[] parent;

    /**
     * Hàm tìm chỗ đỗ trống khả dụng gần nhất theo chiều kim đồng hồ + Nén đường đi
     */
    private static int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = getNextTokenizer(br);

        if (st == null) return;

        int n = parseIntClean(st.nextToken()); // Số chỗ đỗ xe (1 đến n)

        // Khởi tạo DSU vòng tròn
        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        st = getNextTokenizer(br);
        PrintWriter pw = new PrintWriter(System.out);

        for (int i = 0; i < n; i++) {
            if (!st.hasMoreTokens()) {
                st = getNextTokenizer(br);
            }
            int preferredSpot = parseIntClean(st.nextToken());

            // 1. Tìm chỗ đỗ trống thực tế theo chiều kim đồng hồ
            int actualSpot = find(preferredSpot);

            // 2. Xác định chỗ kế tiếp theo chiều kim đồng hồ (n -> 1)
            int nextSpot = (actualSpot % n) + 1;

            // 3. Cập nhật con trỏ chỗ vừa đỗ trỏ tới chỗ trống tiếp theo
            parent[actualSpot] = find(nextSpot);

            // 4. Ghi nhận kết quả
            pw.print(actualSpot + (i == n - 1 ? "" : " "));
        }
        pw.println();
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
