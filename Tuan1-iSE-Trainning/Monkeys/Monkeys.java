import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * BÀI TẬP: MONKEYS (VNOJ Educational DSU)
 * -----------------------------------------------------------------------------
 * ĐỀ BÀI:
 * Có n con khỉ đang bám trên cây (từ 1 đến n). Khỉ 1 là con đầu đàn bám vào cây bằng đuôi.
 * Mỗi con khỉ có 2 tay (tay trái = 1, tay phải = 2), mỗi tay có thể nắm đuôi một con khỉ khác.
 * Trong m giây (từ giây 0 đến m-1), mỗi giây có 1 con khỉ buông 1 tay.
 * Xác định thời điểm từng con khỉ bị rơi khỏi cây (không còn kết nối trực tiếp/gián tiếp với khỉ 1).
 * Nếu không bao giờ rơi, in -1.
 * 
 * THUẬT TOÁN QUAY NGƯỢC THỜI GIAN (REVERSE-TIME DSU):
 * 1. Khởi tạo: Mỗi con khỉ là 1 tập hợp riêng biệt.
 * 2. Xóa cạnh: Đọc danh sách các lượt buông tay và đánh dấu các cạnh sẽ bị buông.
 * 3. Dựng trạng thái cuối (sau m-1 giây): Nối tất cả các cạnh CHƯA BỊ BUÔNG TAY vào DSU.
 * 4. Quay ngược thời gian từ giây m-1 về giây 0 (với biến đếm t):
 *    - Thêm lại cạnh bị buông tại giây t.
 *    - Nếu việc thêm cạnh giúp gộp 1 thành phần chưa kết nối với khỉ 1 vào thành phần chứa khỉ 1,
 *      thì TOÀN BỘ các con khỉ trong thành phần đó sẽ được gán thời gian rơi là t giây.
 * 5. In ra kết quả cho n con khỉ.
 * -----------------------------------------------------------------------------
 */
public class Monkeys {

    // Mảng DSU quản lý cha của từng đỉnh
    private static int[] parent;

    // isMonkey1Connected[r] = true nếu gốc r đang chứa khỉ 1 (hoặc liên thông với khỉ 1)
    private static boolean[] isMonkey1Connected;

    // Danh sách chứa các con khỉ thuộc thành phần liên thông có gốc là r
    private static ArrayList<Integer>[] compMembers;

    // Mảng lưu kết quả thời điểm rơi của từng con khỉ (mặc định là -1)
    private static int[] ans;

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
     * Hàm hợp nhất 2 thành phần liên thông chứa u và v tại thời điểm t
     * @param u đỉnh thứ nhất
     * @param v đỉnh thứ hai
     * @param t thời điểm hiện tại (t từ m-1 về 0; t = -1 đối với các cạnh không bị xóa ban đầu)
     */
    private static void union(int u, int v, int t) {
        int rootU = find(u);
        int rootV = find(v);

        // Nếu đã thuộc cùng một thành phần liên thông thì không làm gì
        if (rootU == rootV) {
            return;
        }

        boolean uHas1 = isMonkey1Connected[rootU];
        boolean vHas1 = isMonkey1Connected[rootV];

        // TRƯỜNG HỢP 1: rootU liên thông với khỉ 1, rootV CHƯA liên thông với khỉ 1
        if (uHas1 && !vHas1) {
            // Tất cả các con khỉ trong nhánh rootV bây giờ mới được nối với khỉ 1 tại giây t!
            if (t != -1) {
                for (int monkey : compMembers[rootV]) {
                    ans[monkey] = t;
                }
            }
            // Gộp nhánh rootV vào rootU
            isMonkey1Connected[rootV] = true;
            compMembers[rootU].addAll(compMembers[rootV]);
            compMembers[rootV] = null; // Giải phóng bộ nhớ
            parent[rootV] = rootU;
        } 
        // TRƯỜNG HỢP 2: rootV liên thông với khỉ 1, rootU CHƯA liên thông với khỉ 1
        else if (vHas1 && !uHas1) {
            // Tất cả các con khỉ trong nhánh rootU bây giờ mới được nối với khỉ 1 tại giây t!
            if (t != -1) {
                for (int monkey : compMembers[rootU]) {
                    ans[monkey] = t;
                }
            }
            // Gộp nhánh rootU vào rootV
            isMonkey1Connected[rootU] = true;
            compMembers[rootV].addAll(compMembers[rootU]);
            compMembers[rootU] = null; // Giải phóng bộ nhớ
            parent[rootU] = rootV;
        } 
        // TRƯỜNG HỢP 3 & 4: Cả 2 cùng chưa nối với khỉ 1, HOẶC cả 2 đều đã nối với khỉ 1 từ trước
        else {
            // Áp dụng gộp tập nhỏ vào tập lớn (Small-to-Large Merge) để tối ưu
            if (compMembers[rootU].size() < compMembers[rootV].size()) {
                int temp = rootU;
                rootU = rootV;
                rootV = temp;
            }
            // Gộp rootV vào rootU
            compMembers[rootU].addAll(compMembers[rootV]);
            compMembers[rootV] = null;
            parent[rootV] = rootU;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = getNextTokenizer(br);

        if (st == null) return;

        int n = parseIntClean(st.nextToken()); // Số con khỉ
        int m = parseIntClean(st.nextToken()); // Số sự kiện buông tay

        // hands[i][0]: tay trái con khỉ i bám vào khỉ nào
        // hands[i][1]: tay phải con khỉ i bám vào khỉ nào
        int[][] hands = new int[n + 1][2];

        for (int i = 1; i <= n; i++) {
            st = getNextTokenizer(br);
            hands[i][0] = parseIntClean(st.nextToken()); // Tay trái (h=1)
            hands[i][1] = parseIntClean(st.nextToken()); // Tay phải (h=2)
        }

        // Lưu thông tin các sự kiện buông tay: events[j] = {p_j, h_j}
        int[][] events = new int[m][2];
        // Dynamic flag để đánh dấu cạnh nào bị buông tay
        boolean[][] removed = new boolean[n + 1][2];

        for (int j = 0; j < m; j++) {
            st = getNextTokenizer(br);
            int p = parseIntClean(st.nextToken());
            int h = parseIntClean(st.nextToken());
            events[j][0] = p;
            events[j][1] = h;

            // Đánh dấu cạnh này sẽ bị buông trong quá trình
            removed[p][h - 1] = true;
        }

        // ---------------------------------------------------------------------
        // BƯỚC 1: KHỞI TẠO DSU BAN ĐẦU (CÁC CON KHỈ RỜI NHAU)
        // ---------------------------------------------------------------------
        parent = new int[n + 1];
        isMonkey1Connected = new boolean[n + 1];
        compMembers = new ArrayList[n + 1];
        ans = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            compMembers[i] = new ArrayList<>();
            compMembers[i].add(i);
            ans[i] = -1; // Mặc định không rơi
        }

        // Khỉ 1 luôn được cố định vào cây bằng đuôi
        isMonkey1Connected[1] = true;

        // ---------------------------------------------------------------------
        // BƯỚC 2: THÊM CÁC CẠNH CHƯA BỊ XÓA (TRẠNG THÁI CUỐI CÙNG SAU M GIÂY)
        // ---------------------------------------------------------------------
        for (int i = 1; i <= n; i++) {
            // Tay trái
            if (hands[i][0] != -1 && !removed[i][0]) {
                union(i, hands[i][0], -1);
            }
            // Tay phải
            if (hands[i][1] != -1 && !removed[i][1]) {
                union(i, hands[i][1], -1);
            }
        }

        // ---------------------------------------------------------------------
        // BƯỚC 3: QUAY NGƯỢC THỜI GIAN TỪ GIÂY M-1 VỀ GIÂY 0
        // ---------------------------------------------------------------------
        for (int t = m - 1; t >= 0; t--) {
            int p = events[t][0];
            int h = events[t][1] - 1; // 0: tay trái, 1: tay phải

            int targetMonkey = hands[p][h];
            if (targetMonkey != -1) {
                // Thêm lại cạnh bị buông tại giây t
                union(p, targetMonkey, t);
            }
        }

        // ---------------------------------------------------------------------
        // BƯỚC 4: IN KẾT QUẢ CHO N CON KHỈ
        // ---------------------------------------------------------------------
        PrintWriter pw = new PrintWriter(System.out);
        for (int i = 1; i <= n; i++) {
            pw.println(ans[i]);
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
     * Hàm parse chuỗi thành int an toàn
     */
    private static int parseIntClean(String str) {
        return Integer.parseInt(str.replaceAll("[^0-9-]", ""));
    }
}
