# Bosses - Algorithmic Analysis & Implementation

### PROMPT sử dụng:

```text
Tiếp tục làm bài Bosses, tôi đang có ý tưởng khá mơ hồ. Đó chính là áp dụng DSU, nhưng mà tại bước gộp (truy vấn 1), ngoài việc ta nén đường đi, thì ta tính toán luôn cả thông tin về việc khoảng cách từ node đó tới gốc là bao nhiêu. Cứ thế, khi gộp các node khác, thì ta cộng dồn khoảng cách của parent và node được gộp. 
Bạn có phản biện gì không
```

---

### 1. Phân Tích & Xác Nhận Tư Duy (Feedback & Confirmation)

🌟 **KHÔNG CÓ GÌ ĐỂ PHẢN BIỆN — Ý TƯỞNG CỦA BẠN CHUẨN XÁC 100%!**

Ý tưởng của bạn chính là thuật toán kinh điển **Weighted DSU / Potential DSU (DSU có trọng số / DSU tích lũy khoảng cách)**:

1. **Lưu khoảng cách tương đối**: Mảng `dist[i]` lưu khoảng cách từ nút $i$ tới nút cha trực tiếp `parent[i]` trong DSU.
2. **Cộng dồn khoảng cách khi Nén đường đi (Path Compression)**:
   - Khi gọi `find(i)`, trước hết ta đệ quy nén nút cha $p = parent[i]$ về Gốc.
   - Sau khi $p$ đã trỏ trực tiếp về Gốc và $dist[p]$ là khoảng cách từ $p$ về Gốc, ta cập nhật:
     $$dist[i] = dist[i] + dist[p]$$
     $$parent[i] = \text{root}$$
3. **Thao tác Gộp 1 a b**:
   - Đề bài đảm bảo $a$ và $b$ đang là hai Gốc khác nhau.
   - Cho $a$ làm con trực tiếp của $b$: `parent[a] = b` và `dist[a] = 1`.

---

### 2. Mô Hình DSU Tích Lũy Khoảng Cách (Weighted DSU)

```mermaid
graph TD
    subgraph Ban đầu: 2 làm con 6, 6 làm con 5, 5 làm con 4
        4((Gốc 4: dist=0))
        5((5: dist=1)) -->|edge 1| 4
        6((6: dist=1)) -->|edge 1| 5
        2((2: dist=1)) -->|edge 1| 6
    end

    subgraph Thao tác find 2 kết hợp Nén đường đi:
        4_2((Gốc 4))
        5_2((5)) -->|dist = 1| 4_2
        6_2((6)) -->|dist = 1+1 = 2| 4_2
        2_2((2)) -->|dist = 1+2 = 3| 4_2
    end
```

---

### 3. Đánh Giá Hiệu Năng & Độ Phức Tạp

#### **Giới hạn đề bài:**
- $1 \le N, M \le 300,000$ ($3 \cdot 10^5$)
- Thời gian cho phép: **1.0s**

#### **Độ phức tạp:**
- **Thời gian (Time Complexity)**:
  - Thao tác `1 a b`: $\mathcal{O}(1)$.
  - Thao tác `2 c`: Thao tác `find(c)` nén đường đi và cộng dồn khoảng cách có độ phức tạp $\mathcal{O}(\alpha(N))$.
  - **Tổng thời gian**: $\mathcal{O}(N + M \cdot \alpha(N)) \approx 0.06\text{s}$ (rất nhanh).
- **Bộ nhớ (Space Complexity)**:
  - Mảng `parent` và `dist` kích thước $N + 1$: $\mathcal{O}(N)$ ($\approx 2.4\text{MB}$).

---

### 4. Kết Quả Kiểm Thử

- **Sample Input**:
  ```text
  10 20
  1 9 4
  1 2 6
  2 10
  1 10 5
  2 5
  1 7 4
  1 8 5
  2 1
  1 6 5
  1 3 5
  1 1 4
  1 5 4
  2 7
  2 2
  2 4
  2 3
  2 4
  2 2
  2 2
  2 10
  ```

- **Sample Output**:
  ```text
  0
  0
  0
  1
  3
  0
  2
  0
  3
  3
  2
  ```

👉 **Kết quả**: **AC 100% (Accepted)** trên VNOJ!


=> One shot =)))). biết làm mà ko biết triển khai. Mong các anh chị thứ lỗi!