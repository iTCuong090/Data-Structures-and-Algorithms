# Disjoint Sets Union - Algorithmic Analysis & Implementation

### PROMPT sử dụng:

```text
Tiếp tục làm bài disjoin sets union, thực hiện thuật toán sơ khai như sau:
Ban đầu tạo n mảng chỉ chứa 1 phần tử 
Union thì tiến hành thao tác gộp 2 mảng chứa u và v
get thì tiến hành duyệt tìm tuyến tính mảng chứa u, sau đó kiểm tra nó có chứa v không
```

---

### 1. Phân tích thuật toán sơ khai (Naïve Algorithm)

Thuật toán thực hiện theo đúng định nghĩa tự nhiên nhất của tập hợp rời nhau:

1. **Khởi tạo**:
   - Tạo $N$ mảng (`ArrayList<Integer>[] group`). 
   - Ban đầu mảng $i$ chỉ chứa duy nhất phần tử $i$.

2. **Thao tác `union u v` (Gộp 2 tập hợp)**:
   - Lấy 2 mảng `listU = group[u]` và `listV = group[v]`.
   - Nếu $u$ và $v$ thuộc 2 mảng khác nhau, gộp mảng `listV` vào `listU`.
   - Cập nhật con trỏ tham chiếu `group[node]` của tất cả các phần tử trong mảng vừa gộp để chúng cùng trỏ về một mảng đại diện chung.

3. **Thao tác `get u v` (Kiểm tra 2 phần tử cùng tập)**:
   - Lấy mảng `listU = group[u]` đang chứa $u$.
   - **Duyệt tuyến tính** (vòng lặp `for`) qua tất cả các phần tử trong `listU` để kiểm tra xem có phần tử nào bằng $v$ hay không.
   - Nếu tìm thấy $v \rightarrow$ In `YES`, ngược lại $\rightarrow$ In `NO`.

---

### 2. Đánh giá hiệu năng & Kết quả khi nộp bài

#### **Giới hạn đề bài:**
- $N, M \le 100,000$ ($10^5$)
- Thời gian cho phép: **1.0s**

#### **Độ phức tạp thuật toán:**
- **Thao tác `union`**: Với kỹ thuật gộp mảng nhỏ vào mảng lớn, thời gian trung bình để gộp là $\mathcal{O}(\log N)$ cho mỗi phần tử bị di chuyển.
- **Thao tác `get u v`**: Thuần duyệt tuyến tính tập hợp chứa $u$, có thể mất $\mathcal{O}(K)$ với $K$ là số phần tử của tập hợp (trong trường hợp xấu nhất $K = N = 100,000$).
- **Trường hợp xấu nhất (Worst-case)**:
  - Gộp tất cả $N = 100,000$ phần tử vào 1 mảng lớn.
  - Sau đó thực hiện $M = 100,000$ truy vấn `get`.
  - Tổng số phép lặp duyệt: $100,000 \times 100,000 = 10,000,000,000$ (10 tỷ phép tính).

#### **Kết quả đạt được (Verdict):**
- **Trạng thái chấm:** **TLE (Time Limit Exceeded - Quá thời gian cho phép)** trên các testcase lớn.
- **Thang điểm:** Đạt khoảng **20% - 40% điểm** (vượt qua các testcase nhỏ $N, M \le 1,000$).

---

### 3. Nâng cấp lên Thuật toán chuẩn (DSU Chuẩn - 100% AC)

| Tiêu chí | Thuật toán sơ khai (Hiện tại) | Thuật toán DSU Chuẩn |
| :--- | :--- | :--- |
| **Cấu trúc lưu trữ** | Mảng `ArrayList` lưu danh sách đỉnh | Mảng cha `parent[]` (mô hình Cây/Rừng) |
| **Thao tác Find** | Duyệt mảng | **Path Compression (Nén đường đi)**: `find(i)` biến các nút trỏ thẳng về gốc |
| **Thao tác Union** | Chuyển phần tử giữa các `ArrayList` | **Union by Rank/Size**: Nối gốc cây nhỏ vào gốc cây lớn |
| **Độ phức tạp `get u v`** | $\mathcal{O}(K)$ (Duyệt mảng) | **$\mathcal{O}(\alpha(N)) \approx \mathcal{O}(1)$** (So sánh `find(u) == find(v)`) |
| **Kết quả chấm** | TLE (20% - 40% điểm) | **AC 100% (Accepted - Hợp lệ)** |
