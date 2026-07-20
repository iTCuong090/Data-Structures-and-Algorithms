PROMPT sử dụng:

Code cho tôi bài Color Query, đặt vào folder tuan1, sử dụng ngôn ngữ java, code vào 1 file duy nhất và có thể bấm nút để chạy hàm main trong đó.

Triển khai theo ĐÚNG thuật toán mà tôi bảo sau đây, mặc kệ nó có sơ khai, mơ hồ. Vì tôi mới bắt đầu học DSA mà bị giã cho quả toàn bài khó vcl nên phải chịu.

Đầu tiên đọc input từ đề bài theo đúng cấu trúc.

Tiếp theo chạy thuật toán, ban đầu có n mảng, mỗi mảng chứa 1 đỉnh.

Gặp truy vấn loại 1 thì hợp nhất 2 mảng chứa u và v.

Gặp truy vấn loại 2 thì thuần duyệt tuyến tính các đỉnh trong mảng đang chứa u, đếm xem có bao nhiêu đỉnh đang chứa màu cần truy vấn.

Chú ý, comment siêu chi tiết.

PROMPT 2:
Thuật toán sơ khai này có gì đặc biệt? Bài này mà chạy thuật toán sơ khai thì đạt ở mức nào?

### 1. Thuật toán sơ khai này có gì đặc biệt?

Thuật toán bạn vừa yêu cầu có 3 đặc điểm chính:

1. **Trực diện & Dễ hình dung nhất (Tư duy ban đầu)**:
   - Thay vì dùng thuật toán nâng cao như **DSU (Disjoint Set Union)** với kỹ thuật nén đường đi (*Path Compression*), thuật toán này sử dụng đúng định nghĩa cơ bản nhất: **Mỗi thành phần liên thông là một danh sách (mảng/`ArrayList`) chứa các đỉnh**.
   - Khi gộp 2 thành phần, ta thực sự gộp 2 danh sách lại với nhau.

2. **Duyệt trâu (Linear Scan)**:
   - Khi được hỏi truy vấn 2 (`2 u c`), thuật toán không hề lưu sẵn số lượng từng màu. Nó **chạy một vòng lặp duyệt từng đỉnh trong mảng** từ đầu đến cuối và đếm thủ công.

3. **Điểm tối ưu "kế thừa" duy nhất (Small-to-Large Merge)**:
   - Phép gộp trong code đã áp dụng mẹo: **Luôn gộp mảng ít phần tử hơn vào mảng nhiều phần tử hơn**. 
   - Điều này giúp phép gộp các mảng không bị quá chậm (tổng thời gian gộp tối đa là $\mathcal{O}(N \log N)$), nhưng **truy vấn loại 2 vẫn là duyệt trâu**.

---

### 2. Khi nộp bài này lên hệ thống chấm (VNOJ), code sẽ đạt ở mức nào?

#### **Giới hạn đề bài:**
- $N \le 100,000$ (số đỉnh)
- $Q \le 200,000$ (số truy vấn)
- Thời gian chạy cho phép: **1.0 giây** (khoảng $10^8$ phép tính).

#### **Đánh giá độ phức tạp thuật toán sơ khai:**
- **Trường hợp xấu nhất (Worst-case):**
  - Tất cả $N = 100,000$ đỉnh được gộp lại thành 1 mảng khổng lồ.
  - Tiếp theo có $Q = 200,000$ truy vấn loại 2 liên tục hỏi màu của mảng này.
  - Mỗi truy vấn loại 2 phải lặp $100,000$ lần.
  - Tổng số phép tính $= 200,000 \times 100,000 = 20,000,000,000$ (20 tỷ phép tính!).

#### **Kết quả đạt được (Verdict):**
- **Trạng thái chấm:** Bị **TLE (Time Limit Exceeded - Quá thời gian cho phép)** trên các testcase lớn.
- **Thang điểm:** Đạt khoảng **20% - 40% điểm** (ăn trọn các testcase nhỏ $N, Q \le 1,000$).

---

### 3. Con đường nâng cấp lên 100% điểm (AC)

Để đạt full điểm bài này sau khi bạn đã hiểu bài toán qua thuật toán sơ khai:

| Tiêu chí | Thuật toán sơ khai (Hiện tại) | Thuật toán chuẩn (DSU + Map) |
| :--- | :--- | :--- |
| **Quản lý mảng/tập hợp** | Mảng `ArrayList` | **DSU** (`parent[]` + `find()`) |
| **Đếm màu** | Duyệt trâu từng phần tử $\mathcal{O}(K)$ | **Dùng `HashMap<Integer, Integer>`** đếm sẵn số lượng từng màu |
| **Truy vấn 2 (`2 u c`)** | $\mathcal{O}(K)$ (rất chậm) | **$\mathcal{O}(1)$** (tra cứu trực tiếp trong `Map`) |
| **Đánh giá** | TLE (20-40% điểm) | **AC 100% (Accepted)** |