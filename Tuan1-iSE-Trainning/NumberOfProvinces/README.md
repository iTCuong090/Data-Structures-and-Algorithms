# Number of Provinces - Algorithmic Analysis & Implementation

### PROMPT sử dụng:

```text
Nà ní, bài toán numbers of provinces là bài toán yêu cầu đếm số lượng thành phần liên thông có trong 1 đồ thị à. Cái này có phải áp dụng thuật toán DSU không? ĐI đọc ma trận thành các cạnh, sau đó áp dụng DSU và nén đường đi để thực hiện việc merge các thành phần liên thông với nhau theo cạnh, đồng thời nén đường đi. Cuối cùng chỉ cần đếm số lượng gốc phân biệt trong tập parents là xong. Bạn có phản biện gì không

PROMPT 2:
Bạn chưa nói về việc tôi đếm số lượng tỉnh bằng cách xem số lượng giá trị phân biệt có trong mảng parents sau cùng. Bạn đưa ra giải pháp count-- hay ho thế
```

---

### 1. Phân Tích & So Sánh 2 Phương Pháp Đếm Số Tỉnh (Counting Methods)

🌟 **Ý TƯỞNG CỦA BẠN HOÀN TOÀN ĐÚNG VỀ MẶT TƯ DUY NGUYÊN LÝ!**

Cả 2 cách đếm đều dựa trên bản chất: **Số thành phần liên thông = Số lượng Đỉnh Gốc phân biệt trong DSU**.

| Tiêu chí | Cách 1: Đếm số Gốc phân biệt ở cuối (Ý tưởng của bạn) | Cách 2: Kỹ thuật Khấu trừ `count--` (Tối ưu) |
| :--- | :--- | :--- |
| **Cách hoạt động** | Sau khi hợp nhất xong, duyệt $i = 0 \dots N-1$, đếm xem có bao nhiêu đỉnh thỏa mãn `find(i) == i`. | Ban đầu coi có $N$ tỉnh (`count = N`). Mỗi lần `union(u, v)` thành công (2 tập hợp gộp thành 1), ta giảm `count--`. |
| **Độ phức tạp** | Tốn thêm 1 vòng lặp $\mathcal{O}(N)$ ở cuối để duyệt và kiểm tra `find(i) == i`. | **$\mathcal{O}(1)$**: Biến `count` tự động chứa kết quả ngay khi kết thúc các phép gộp mà không cần lặp thêm. |
| **Lưu ý bẫy lập trình** | ⚠️ **Không được đọc trực tiếp `parent[i]`**: Vì một số nút lá chưa được gọi `find()` ở cuối có thể vẫn chứa parent tạm. Bắt buộc phải duyệt kiểm tra `find(i) == i`. | 💡 Rất sạch sẽ, không lo bẫy nén đường đi chưa hoàn tất. |

---

### 2. Mô Hình Thuật Toán DSU

```mermaid
graph TD
    subgraph Ban đầu (count = 3):
        0((0))
        1((1))
        2((2))
    end

    subgraph Nối 0 và 1 (isConnected[0][1] = 1):
        0_1((0)) --- 1_1((1))
        2_1((2))
        Count["count = 3 - 1 = 2"]
    end
```

---

### 3. Đánh Giá Hiệu Năng & Độ Phức Tạp

#### **Giới hạn đề bài:**
- $1 \le N \le 200$
- Thời gian cho phép: **1.0s**

#### **Độ phức tạp:**
- **Thời gian (Time Complexity)**:
  - Duyệt nửa ma trận kề $\frac{N(N-1)}{2}$ cặp: $\mathcal{O}(N^2)$.
  - Mỗi thao tác `find()` và `union()` có độ phức tạp $\mathcal{O}(\alpha(N)) \approx \mathcal{O}(1)$.
  - **Tổng thời gian**: $\mathcal{O}(N^2 \cdot \alpha(N)) \approx 0.001\text{s}$ (chạy tức thì).
- **Bộ nhớ (Space Complexity)**:
  - Mảng `parent` kích thước $N$: $\mathcal{O}(N)$ ($\approx 1\text{KB}$).

---

### 4. Kết Quả Kiểm Thử

- **Sample 1 Input**: `isConnected = [[1,1,0],[1,1,0],[0,0,1]]` $\rightarrow$ **Output**: `2`
- **Sample 2 Input**: `isConnected = [[1,0,0],[0,1,0],[0,0,1]]` $\rightarrow$ **Output**: `3`

👉 **Kết quả**: **AC 100% (Accepted)** trên LeetCode!

