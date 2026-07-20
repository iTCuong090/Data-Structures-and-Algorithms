
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * BÀI TẬP: COLOR QUERY (VNOJ)
 * -----------------------------------------------------------------------------
 * ĐỀ BÀI:
 * Cho đồ thị n đỉnh (đánh số từ 1 đến n), đỉnh i có màu colors[i].
 * Có 2 loại truy vấn:
 * - 1 u v : Thêm cạnh nối u và v (gộp 2 thành phần liên thông chứa u và v).
 * - 2 u c : Đếm số đỉnh có màu c trong thành phần liên thông chứa đỉnh u.
 * 
 * YÊU CẦU THUẬT TOÁN (SƠ KHAI / MẢNG ĐƠN GIẢN):
 * 1. Ban đầu có n mảng độc lập, mảng thứ i chỉ chứa 1 đỉnh duy nhất là đỉnh i.
 * 2. Truy vấn loại 1 (1 u v): Hợp nhất 2 mảng chứa u và v làm một.
 * 3. Truy vấn loại 2 (2 u c): Duyệt tuyến tính tất cả các đỉnh trong mảng đang
 * chứa u,
 * đếm số lượng đỉnh có màu c.
 * -----------------------------------------------------------------------------
 */
public class ColorQuery {

    public static void main(String[] args) throws IOException {
        // =====================================================================
        // BƯỚC 1: ĐỌC DỮ LIỆU ĐẦU VÀO (FAST I/O)
        // =====================================================================
        // Sử dụng BufferedReader và StringTokenizer để đọc input nhanh chóng,
        // tránh bị quá thời gian khi làm việc với số lượng dữ liệu lớn.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = getNextTokenizer(br);

        if (st == null)
            return;

        // n: số lượng đỉnh của đồ thị (từ 1 đến n)
        int n = parseIntClean(st.nextToken());
        // q: số lượng truy vấn
        int q = parseIntClean(st.nextToken());

        // Mảng lưu trữ màu của từng đỉnh (Đỉnh đánh số từ 1 đến n nên dùng mảng kích
        // thước n + 1)
        int[] colors = new int[n + 1];

        // Đọc dòng thứ 2: màu của n đỉnh
        st = getNextTokenizer(br);
        for (int i = 1; i <= n; i++) {
            if (!st.hasMoreTokens()) {
                st = getNextTokenizer(br);
            }
            colors[i] = parseIntClean(st.nextToken());
        }

        // =====================================================================
        // BƯỚC 2: KHỞI TẠO CẤU TRÚC MẢNG CHO N ĐỈNH
        // =====================================================================
        // Sử dụng một mảng các Danh sách (ArrayList): group[i] đại diện cho mảng các
        // đỉnh
        // thuộc cùng thành phần liên thông đang chứa đỉnh i.
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] group = new ArrayList[n + 1];

        // Ban đầu, có n mảng. Mảng thứ i chỉ chứa đúng 1 đỉnh là đỉnh i.
        for (int i = 1; i <= n; i++) {
            group[i] = new ArrayList<>();
            group[i].add(i); // Thêm đỉnh i vào mảng của chính nó
        }

        // Dùng StringBuilder để gom các kết quả in ra một lần, tối ưu hiệu năng I/O
        StringBuilder sb = new StringBuilder();

        // =====================================================================
        // BƯỚC 3: XỬ LÝ LẦN LƯỢT Q TRUY VẤN
        // =====================================================================
        for (int i = 0; i < q; i++) {
            st = getNextTokenizer(br);
            if (st == null)
                break;

            int type = parseIntClean(st.nextToken()); // Loại truy vấn (1 hoặc 2)

            if (type == 1) {
                // -------------------------------------------------------------
                // TRUY VẤN LOẠI 1: 1 u v (Hợp nhất mảng chứa u và mảng chứa v)
                // -------------------------------------------------------------
                int u = parseIntClean(st.nextToken());
                int v = parseIntClean(st.nextToken());

                // Lấy mảng đại diện hiện tại của u và v
                ArrayList<Integer> listU = group[u];
                ArrayList<Integer> listV = group[v];

                // Nếu u và v chưa ở chung một mảng thì tiến hành gộp mảng
                if (listU != listV) {
                    // Tối ưu gộp mảng: Luôn gộp mảng có ít phần tử hơn vào mảng có nhiều phần tử
                    // hơn
                    // để giảm số lượng phép gộp và cập nhật con trỏ.
                    if (listU.size() < listV.size()) {
                        ArrayList<Integer> temp = listU;
                        listU = listV;
                        listV = temp;
                    }

                    // Chuyển toàn bộ phần tử từ listV sang listU
                    for (int node : listV) {
                        listU.add(node);
                        // Cập nhật lại con trỏ tham chiếu của đỉnh 'node' trỏ tới mảng chung 'listU'
                        group[node] = listU;
                    }
                }

            } else if (type == 2) {
                // -------------------------------------------------------------
                // TRUY VẤN LOẠI 2: 2 u c (Duyệt tuyến tính mảng chứa u để đếm màu c)
                // -------------------------------------------------------------
                int u = parseIntClean(st.nextToken());
                int c = parseIntClean(st.nextToken());

                // Lấy mảng đang chứa đỉnh u
                ArrayList<Integer> currentGroup = group[u];

                // Đếm số đỉnh trong mảng có màu bằng c
                int count = 0;

                // Thuần duyệt tuyến tính (lặp qua từng đỉnh trong mảng)
                for (int node : currentGroup) {
                    if (colors[node] == c) {
                        count++; // Tìm thấy 1 đỉnh có màu c -> tăng biến đếm
                    }
                }

                // Lưu kết quả vào StringBuilder
                sb.append(count).append("\n");
            }
        }

        // In tất cả kết quả của các truy vấn loại 2 ra màn hình
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
        if (line == null)
            return null;
        return new StringTokenizer(line);
    }

    /**
     * Hàm ép kiểu String sang Int sạch (bỏ các ký tự không phải số như BOM)
     */
    private static int parseIntClean(String s) {
        String cleaned = s.replaceAll("[^0-9]", "");
        return Integer.parseInt(cleaned);
    }
}
