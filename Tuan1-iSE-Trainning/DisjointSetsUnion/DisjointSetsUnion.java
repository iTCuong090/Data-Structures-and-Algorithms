import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * BÀI TẬP: DISJOINT SETS UNION (VNOJ Educational DSU)
 * -----------------------------------------------------------------------------
 * ĐỀ BÀI:
 * Duy trì n phần tử được chia thành các tập rời nhau. Ban đầu mỗi phần tử nằm trong 1 tập.
 * Xử lý 2 loại truy vấn:
 *   - union u v : gộp hai tập đang chứa u và v.
 *   - get u v   : kiểm tra u và v có thuộc cùng một tập hay không (in YES hoặc NO).
 * 
 * YÊU CẦU THUẬT TOÁN SƠ KHAI (MẢNG ĐƠN GIẢN):
 * 1. Ban đầu tạo n mảng chỉ chứa 1 phần tử.
 * 2. Khi gặp 'union u v': Tiến hành thao tác gộp 2 mảng chứa u và v làm một.
 * 3. Khi gặp 'get u v'  : Tiến hành duyệt tuyến tính toàn bộ mảng đang chứa u, 
 *    sau đó kiểm tra xem mảng đó có chứa v hay không.
 * -----------------------------------------------------------------------------
 */
public class DisjointSetsUnion {

    public static void main(String[] args) throws IOException {
        // =====================================================================
        // BƯỚC 1: ĐỌC DỮ LIỆU ĐẦU VÀO (FAST I/O)
        // =====================================================================
        // Sử dụng BufferedReader để đọc dữ liệu vào nhanh chóng
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = getNextTokenizer(br);

        if (st == null) return;

        // n: Số lượng phần tử (từ 1 đến n)
        int n = parseIntClean(st.nextToken());
        // m: Số lượng truy vấn (union hoặc get)
        int m = parseIntClean(st.nextToken());

        // =====================================================================
        // BƯỚC 2: KHỞI TẠO N MẢNG BAN ĐẦU
        // =====================================================================
        // group[i] đại diện cho mảng các phần tử thuộc cùng một tập hợp chứa phần tử i.
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] group = new ArrayList[n + 1];

        // Ban đầu, có n mảng độc lập. Mảng thứ i chỉ chứa 1 phần tử duy nhất là i.
        for (int i = 1; i <= n; i++) {
            group[i] = new ArrayList<>();
            group[i].add(i); // Thêm phần tử i vào mảng của chính nó
        }

        // StringBuilder để tối ưu hóa việc in ra màn hình
        StringBuilder sb = new StringBuilder();

        // =====================================================================
        // BƯỚC 3: XỬ LÝ LẦN LƯỢT M TRUY VẤN
        // =====================================================================
        for (int i = 0; i < m; i++) {
            st = getNextTokenizer(br);
            if (st == null) break;

            String type = st.nextToken(); // Loại truy vấn: "union" hoặc "get"
            int u = parseIntClean(st.nextToken());
            int v = parseIntClean(st.nextToken());

            if (type.equalsIgnoreCase("union")) {
                // -------------------------------------------------------------
                // THAO TÁC UNION: Gộp 2 mảng chứa u và v
                // -------------------------------------------------------------
                ArrayList<Integer> listU = group[u];
                ArrayList<Integer> listV = group[v];

                // Nếu u và v chưa thuộc cùng một mảng thì tiến hành gộp
                if (listU != listV) {
                    // Tối ưu gộp mảng: Gộp mảng nhỏ vào mảng lớn để giảm số lần cập nhật con trỏ
                    if (listU.size() < listV.size()) {
                        ArrayList<Integer> temp = listU;
                        listU = listV;
                        listV = temp;
                    }

                    // Chuyển tất cả phần tử từ listV sang listU
                    for (int node : listV) {
                        listU.add(node);
                        // Cập nhật con trỏ tham chiếu của 'node' sang mảng chung mới 'listU'
                        group[node] = listU;
                    }
                }

            } else if (type.equalsIgnoreCase("get")) {
                // -------------------------------------------------------------
                // THAO TÁC GET: Duyệt tuyến tính mảng chứa u xem có chứa v không
                // -------------------------------------------------------------
                ArrayList<Integer> listU = group[u];
                boolean found = false;

                // Thuần duyệt tuyến tính: Lặp qua tất cả phần tử trong mảng chứa u
                for (int node : listU) {
                    if (node == v) {
                        found = true; // Tìm thấy v trong mảng chứa u
                        break;
                    }
                }

                // Nếu tìm thấy v trong mảng chứa u -> in YES, ngược lại -> in NO
                if (found) {
                    sb.append("YES\n");
                } else {
                    sb.append("NO\n");
                }
            }
        }

        // In tất cả kết quả của các truy vấn 'get' ra màn hình
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
