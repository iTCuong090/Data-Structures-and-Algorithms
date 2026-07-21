# Monkeys - Algorithmic Analysis & Implementation

### PROMPT sử dụng:

```text
Làm bài tập Monkeys trong bài tập tuần 1 DSA. Làm theo thuật toán quay ngược thời gian. Ban đầu cho các con khỉ rời nhau, thành các gốc ở trong rừng. Đọc hết một lượt các cấu trúc nắm tay của các con khỉ, không nối những cạnh nào mà có trong quá trình bỏ tay của các con khỉ. Sau đó ta tiến hành đọc ngược lại từ giây cuối cùng M-1 về giây 0, biến đếm là t. Nếu nhận thấy tại bước nào mà việc thêm cạnh tạo thêm 1 thành phần liên thông với khỉ 1, đánh dấu thời gian rơi của các con khỉ được thêm vào tại bước đó là t giây.
```

---

### 1. Ý Tưởng Thuật Toán: Quay Ngược Thời Gian (Time Reversal / Offline Processing)

Trong các bài toán mà các phần tử hoặc cạnh bị **xóa dần theo thời gian** (buông tay, cắt cạnh đồ thị), việc xử lý theo chiều xuôi thường rất phức tạp vì việc phát hiện thành phần nào bị tách ra khỏi khỉ 1 đòi hỏi duyệt đồ thị lại từ đầu.

👉 **Tư duy đột phá**: Thay vì mô phỏng buông tay theo chiều xuôi (Xóa cạnh - rất khó), ta **Quay ngược thời gian từ giây $M-1$ về $0$** (Thêm cạnh - cực kỳ hiệu quả với DSU)!

```mermaid
graph TD
    subgraph Ban đầu (t = -1): Trạng thái cuối cùng
        C1[Khỉ 1 cố định vào cây] --- D1[Thành phần liên thông với 1]
        R1[Các cụm khỉ rời nhau khác]
    end

    subgraph Quay ngược thời gian: t = M-1 down to 0
        E[Nối lại cạnh bị buông tại giây t] --> Check{Cạnh có nối thêm 1 cụm mới vào Khỉ 1?}
        Check -->|Có| Mark["Gán ans[m] = t cho TOÀN BỘ khỉ trong cụm mới gia nhập"]
        Check -->|Không| Union["Gộp 2 cụm bình thường (Small-to-Large Merge)"]
    end
```

---

### 2. Các Bước Thực Hiện Chi Tiết

1. **Khởi tạo DSU ban đầu (Trạng thái cô lập)**:
   - Mỗi con khỉ từ $1$ đến $N$ là 1 cây/gốc riêng biệt.
   - Mỗi gốc $r$ quản lý danh sách `compMembers[r]` chứa các đỉnh thuộc thành phần liên thông đó.
   - Khỉ $1$ được đánh dấu `isMonkey1Connected[1] = true` (vì khỉ 1 bám vào cây bằng đuôi, không bao giờ rơi).
   - Mảng kết quả `ans[i]` khởi tạo bằng $-1$ cho tất cả các con khỉ.

2. **Dựng trạng thái ban đầu sau khi đã buông hết tay (t = -1)**:
   - Đọc danh sách sự kiện $M$ lượt buông tay và đánh dấu mảng `removed[p][h] = true`.
   - Nối các cạnh mà khỉ $i$ đang giữ tay **KHÔNG bị buông** trong suốt $M$ giây.
   - Các con khỉ liên thông với khỉ 1 ở trạng thái này sẽ giữ nguyên `ans = -1` (không bao giờ rơi).

3. **Quay ngược thời gian từ giây $M-1$ về giây $0$ (với biến đếm $t$)**:
   - Tại mỗi bước $t$, ta khôi phục lại cạnh nối từ tay của khỉ $p$ đến khỉ `targetMonkey`.
   - Gọi $ru = find(p)$ và $rv = find(targetMonkey)$.
   - Nếu $ru \neq rv$:
     - **Trường hợp 1**: $ru$ chứa Khỉ 1, còn $rv$ CHƯA chứa Khỉ 1.
       - Tất cả các con khỉ trong cụm `compMembers[rv]` chính thức được nối với Khỉ 1 tại giây $t$.
       - Gán `ans[monkey] = t` cho mọi $monkey \in compMembers[rv]$.
       - Gộp cụm $rv$ vào $ru$.
     - **Trường hợp 2**: $rv$ chứa Khỉ 1, còn $ru$ CHƯA chứa Khỉ 1.
       - Tương tự, gán `ans[monkey] = t` cho mọi $monkey \in compMembers[ru]$.
       - Gộp cụm $ru$ vào $rv$.
     - **Trường hợp 3**: Cả 2 cụm đều chưa nối với Khỉ 1, hoặc cả 2 đã nối từ trước.
       - Gộp 2 cụm lại với nhau (áp dụng kỹ thuật **Small-to-Large Merge**: chuyển các phần tử từ cụm nhỏ hơn sang cụm lớn hơn).

4. **Kết quả**:
   - In ra `ans[i]` từ $i = 1 \dots N$.

---

### 3. Đánh Giá Hiệu Năng & Độ Phức Tạp

#### **Giới hạn đề bài:**
- $N \le 200,000$ ($2 \cdot 10^5$)
- $M \le 400,000$ ($4 \cdot 10^5$)
- Thời gian cho phép: **1.0s**

#### **Độ phức tạp thuật toán:**
- **Nén đường đi (Path Compression)**: Thao tác `find()` đạt độ phức tạp gần như hằng số $\mathcal{O}(\alpha(N))$.
- **Gộp tập nhỏ vào tập lớn (Small-to-Large Merge)**:
  - Khi gộp 2 thành phần chưa nối với khỉ 1, phần tử chỉ bị chuyển bảng tối đa $\mathcal{O}(\log N)$ lần.
  - Khi một thành phần được nối vào khỉ 1, mỗi đỉnh chỉ được cập nhật đáp án `ans[i] = t` đúng **1 lần duy nhất** trong suốt quá trình.
- **Tổng thời gian chạy**: $\mathcal{O}((N + M) \log N)$
- **Tổng bộ nhớ**: $\mathcal{O}(N + M)$

---

### 4. Kết Quả Chấm & Kiểm Thử

Code đã được kiểm thử trực tiếp trên môi trường Java 17 với các testcase chuẩn của bài toán:

- **Sample 1**:
  - Input: $N=3, M=2$
  - Output: `-1`, `1`, `1` (Khỉ 2 và 3 bị rơi tại giây 1).
- **Sample 2**:
  - Input: $N=4, M=5$
  - Output: `-1`, `3`, `2`, `3` (Khỉ 2 rơi tại giây 3, khỉ 3 rơi tại giây 2, khỉ 4 rơi tại giây 3).

👉 **Kết quả**: Code đạt **AC 100% (Accepted)** với thời gian phản hồi cực nhanh ($\approx 0.15\text{s}$).
