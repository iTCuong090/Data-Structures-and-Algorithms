import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BÀI TẬP: TRÒ CON BÒ (VNOJ Educational DSU)
 * -----------------------------------------------------------------------------
 * ĐỀ BÀI:
 * N chuồng bò (độc lập ban đầu). Có K lệnh:
 *   - join X Y : Nối chuồng X và chuồng Y (gộp 2 nhóm chuồng).
 *   - add X V  : Thêm V con bò cho chuồng X và TẤT CẢ các chuồng đang kết nối với X.
 *   - get X    : Hỏi số bò hiện có tại chuồng X.
 * 
 * BẤT NGỜ THÚ VỊ & BẪY THỜI GIAN (POTENTIAL DIFFERENCE):
 * - Khi add X V: Ta cộng V trực tiếp vào Gốc đại diện rootX.
 * - Nhưng khi join rootY vào rootX: rootY MỚI GỘP VÀO, không được hưởng số bò
 *   đã cộng ở quá khứ của rootX.
 * - Giải pháp: Thiết lập độ lệch bò (Offset):
 *   cows[rootY] = cows[rootY] - cows[rootX];
 *   khi đó số bò thực tế của nút i = cows[i] + cows[root].
 * -----------------------------------------------------------------------------
 */
public class TroConBo {

    private static int[] parent; // Mảng nút cha
    private static int[] cows;   // Mảng lưu chênh lệch bò so với nút cha

    /**
     * Hàm tìm Gốc đại diện + Nén đường đi + Cập nhật chênh lệch bò relative với Gốc
     */
    private static int find(int i) {
        if (parent[i] == i) {
            return i; // i là Gốc
        }
        int p = parent[i];
        int root = find(p); // Đệ quy nén nút cha p về Gốc trước

        // Nếu p chưa phải là Gốc, cập nhật chênh lệch của i theo Gốc và gán parent[i] = root
        if (p != root) {
            cows[i] += cows[p];
            parent[i] = root;
        }
        return root;
    }

    /**
     * Lệnh join X Y: Gộp 2 nhóm chứa X và Y
     */
    private static void join(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            // Cho rootY nhận rootX làm cha
            parent[rootY] = rootX;

            // BẪY CỐT LÕI: rootY gia nhập nhóm rootX sau, nên phải trừ đi số bò 
            // tích lũy của rootX ở quá khứ để khi tính (cows[rootY] + cows[rootX]) kết quả vẫn chuẩn!
            cows[rootY] -= cows[rootX];
        }
    }

    /**
     * Lệnh add X V: Thêm V con bò cho tất cả chuồng trong nhóm chứa X
     */
    private static void add(int x, int v) {
        int rootX = find(x);
        // Cộng V trực tiếp vào Gốc đại diện của nhóm
        cows[rootX] += v;
    }

    /**
     * Lệnh get X: Hỏi số bò hiện tại ở chuồng X
     */
    private static int get(int x) {
        int root = find(x);
        if (x == root) {
            return cows[root];
        }
        // Số bò của x = Chênh lệch cows[x] + Số bò tại Gốc cows[root]
        return cows[x] + cows[root];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = getNextTokenizer(br);

        if (st == null) return;

        int n = parseIntClean(st.nextToken()); // Số chuồng bò
        int k = parseIntClean(st.nextToken()); // Số lệnh

        parent = new int[n + 1];
        cows = new int[n + 1];

        // Ban đầu mỗi chuồng là 1 gốc riêng, chưa có bò nào
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            cows[i] = 0;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < k; i++) {
            st = getNextTokenizer(br);
            if (st == null) break;

            String cmd = st.nextToken();

            if (cmd.equalsIgnoreCase("join")) {
                int x = parseIntClean(st.nextToken());
                int y = parseIntClean(st.nextToken());
                join(x, y);

            } else if (cmd.equalsIgnoreCase("add")) {
                int x = parseIntClean(st.nextToken());
                int v = parseIntClean(st.nextToken());
                add(x, v);

            } else if (cmd.equalsIgnoreCase("get")) {
                int x = parseIntClean(st.nextToken());
                sb.append(get(x)).append("\n");
            }
        }

        System.out.print(sb.toString());
    }

    private static StringTokenizer getNextTokenizer(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (line != null && line.trim().isEmpty()) {
            line = br.readLine();
        }
        if (line == null) return null;
        return new StringTokenizer(line);
    }

    private static int parseIntClean(String s) {
        String cleaned = s.replaceAll("[^0-9]", "");
        return Integer.parseInt(cleaned);
    }
}
