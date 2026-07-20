import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BÀI TẬP: GIÁ TRỊ TẬP (VNOJ Educational DSU)
 * -----------------------------------------------------------------------------
 * ĐỀ BÀI:
 * Đồ thị n đỉnh (đánh số từ 1 đến n). Ban đầu đỉnh thứ i có giá trị bằng i.
 * Có m truy vấn thuộc 2 loại:
 *   - union u v : Nối cạnh giữa u và v (gộp 2 thành phần liên thông).
 *   - get u     : Đưa ra thông tin thành phần liên thông chứa u gồm 3 số:
 *                 [Giá trị nhỏ nhất, Giá trị lớn nhất, Kích thước (số đỉnh)].
 * 
 * THUẬT TOÁN TỐI ƯU (DSU + CACHE TẠI NÚT GỐC):
 * 1. Tư duy Nén Cây (Path Compression): Mỗi thành phần liên thông là 1 cây, 
 *    nút Gốc (Root) làm đại diện. Kỹ thuật Nén đường đi giúp cây ép phẳng về dạng 2 tầng.
 * 2. Cache tại Gốc: Duy trì minVal[], maxVal[], size[] CHỈ TẠI NÚT GỐC.
 * 3. Thao tác union u v: Gộp 2 gốc rootU và rootV, cập nhật min, max, size mới
 *    cho gốc duy nhất trong O(1).
 * 4. Thao tác get u: Tìm gốc đại diện của u trong O(1), đọc ngay 3 thông tin cache ra.
 * -----------------------------------------------------------------------------
 */
public class GiaTriTap {

    // mảng parent[i]: Lưu nút cha của đỉnh i. Nếu parent[i] == i thì i là Nút Gốc.
    private static int[] parent;
    
    // Mảng lưu trữ thông tin cache TẠI NÚT GỐC
    private static int[] minVal; // Giá trị đỉnh nhỏ nhất trong tập
    private static int[] maxVal; // Giá trị đỉnh lớn nhất trong tập
    private static int[] size;   // Kích thước (số lượng đỉnh) của tập

    /**
     * Hàm tìm Nút Gốc đại diện của đỉnh i (áp dụng Nén đường đi - Path Compression)
     */
    private static int find(int i) {
        if (parent[i] == i) {
            return i; // i chính là Nút Gốc
        }
        // Nén đường đi: Gán trực tiếp parent[i] bằng gốc tìm được
        // Giúp các lần tìm kiếm sau chỉ mất O(1)
        return parent[i] = find(parent[i]);
    }

    /**
     * Hàm gộp 2 tập hợp chứa u và v (Union by Size)
     */
    private static void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        // Nếu u và v thuộc 2 tập hợp khác nhau thì mới gộp
        if (rootU != rootV) {
            // Tối ưu gộp theo kích thước: Luôn gộp cây nhỏ hơn vào cây lớn hơn
            if (size[rootU] < size[rootV]) {
                int temp = rootU;
                rootU = rootV;
                rootV = temp;
            }

            // Cho rootV trỏ làm con của rootU
            parent[rootV] = rootU;

            // =================================================================
            // CẬP NHẬT CACHE THÔNG TIN DUY NHẤT TẠI NÚT GỐC MỚI (rootU) trong O(1)
            // =================================================================
            size[rootU] += size[rootV];
            minVal[rootU] = Math.min(minVal[rootU], minVal[rootV]);
            maxVal[rootU] = Math.max(maxVal[rootU], maxVal[rootV]);
        }
    }

    public static void main(String[] args) throws IOException {
        // Fast I/O với BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = getNextTokenizer(br);

        if (st == null) return;

        // n: số lượng đỉnh, m: số lượng truy vấn
        int n = parseIntClean(st.nextToken());
        int m = parseIntClean(st.nextToken());

        // Khởi tạo các mảng dữ liệu (đỉnh đánh số từ 1 đến n)
        parent = new int[n + 1];
        minVal = new int[n + 1];
        maxVal = new int[n + 1];
        size = new int[n + 1];

        // Ban đầu, mỗi đỉnh i tạo thành 1 cây riêng lẻ (chính nó là Gốc)
        for (int i = 1; i <= n; i++) {
            parent[i] = i;  // Gốc của i là chính i
            minVal[i] = i;  // Min ban đầu là i
            maxVal[i] = i;  // Max ban đầu là i
            size[i] = 1;    // Kích thước ban đầu là 1
        }

        StringBuilder sb = new StringBuilder();

        // Xử lý m truy vấn
        for (int i = 0; i < m; i++) {
            st = getNextTokenizer(br);
            if (st == null) break;

            String type = st.nextToken();

            if (type.equalsIgnoreCase("union")) {
                int u = parseIntClean(st.nextToken());
                int v = parseIntClean(st.nextToken());
                union(u, v);

            } else if (type.equalsIgnoreCase("get")) {
                int u = parseIntClean(st.nextToken());

                // Tìm Nút Gốc đại diện cho thành phần liên thông chứa u
                int root = find(u);

                // Lấy ngay thông tin đã cache tại Nút Gốc trong O(1)
                sb.append(minVal[root]).append(" ")
                  .append(maxVal[root]).append(" ")
                  .append(size[root]).append("\n");
            }
        }

        // In toàn bộ kết quả ra màn hình
        System.out.print(sb.toString());
    }

    /**
     * Hàm hỗ trợ lấy StringTokenizer cho dòng tiếp theo không rỗng
     */
    private static StringTokenizer getNextTokenizer(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (line != null && line.trim().isEmpty()) {
            line = br.readLine();
        }
        if (line == null) return null;
        return new StringTokenizer(line);
    }

    /**
     * Hàm ép kiểu String sang Int sạch (bỏ các ký tự rác phi số như BOM)
     */
    private static int parseIntClean(String s) {
        String cleaned = s.replaceAll("[^0-9]", "");
        return Integer.parseInt(cleaned);
    }
}
