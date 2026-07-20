# **Bài tập Tuần 1**

# **Union-Find / Disjoint Set Union**

### **Color query \- VNOJ**

*Nguồn: https://oj.vnoi.info/problem/colquery*

Cho một đồ thị vô hướng có n đỉnh, đỉnh thứ i có màu a\_i. Ban đầu đồ thị chưa có cạnh nào.

Bạn cần xử lý q truy vấn. Truy vấn loại 1 thêm một cạnh giữa hai đỉnh u và v, làm thay đổi các thành phần liên thông hiện tại. Truy vấn loại 2 yêu cầu xét thành phần liên thông đang chứa đỉnh u và đếm số đỉnh có màu c trong thành phần đó.

**Yêu cầu:** Với mỗi truy vấn loại 2, in số đỉnh có màu được hỏi trong component hiện tại của u.

**Input**

Dòng đầu tiên chứa hai số nguyên dương n và q.  
Dòng thứ hai chứa n số nguyên dương a\_1, a\_2, ..., a\_n là màu của từng đỉnh.  
q dòng cuối cùng, mỗi dòng gồm ba số nguyên dương. Nếu số đầu là 1 thì truy vấn có dạng 1 u v để thêm cạnh. Nếu số đầu là 2 thì truy vấn có dạng 2 u c để hỏi số đỉnh màu c trong component chứa u.

**Output**

Với mỗi truy vấn loại 2, in ra một dòng là kết quả của truy vấn đó.

**Giới hạn**

n \<= 100000; q \<= 200000; a\_i \<= n; các tham số u, v, c trong truy vấn không vượt quá n.

**Sample Input**

5 7  
2 4 2 3 2  
1 1 2  
2 1 2  
1 3 5  
2 2 1  
1 1 3  
2 3 2  
2 4 3

**Sample Output**

1  
0  
3  
1

### 

### 

### 

### **Disjoint Sets Union \- VNOJ Educational DSU**

*Nguồn: https://oj.vnoi.info/contest/dsu/all*

Để xây dựng cấu trúc dữ liệu Disjoint Set Union, ta cần duy trì n phần tử được chia thành các tập rời nhau. Ban đầu mỗi phần tử nằm trong một tập riêng.

Có hai thao tác: union u v để gộp hai tập đang chứa u và v, và get u v để kiểm tra hai phần tử hiện có thuộc cùng một tập hay không.

**Yêu cầu:** Xử lý lần lượt các truy vấn và trả lời đúng mọi truy vấn get.

**Input**

Dòng đầu tiên gồm hai số nguyên n và m là số phần tử và số truy vấn.  
m dòng tiếp theo, mỗi dòng là một truy vấn:  
\- union u v: gộp hai tập chứa u và v.  
\- get u v: kiểm tra u và v có cùng tập hay không.

**Output**

Với mỗi truy vấn get, in YES nếu hai phần tử thuộc cùng một tập, ngược lại in NO.

**Giới hạn**

1 \<= n, m \<= 10^5.

**Sample Input**

4 4  
union 1 2  
union 1 3  
get 1 4  
get 2 3

**Sample Output**

NO  
YES

### **Giá trị tập \- VNOJ Educational DSU**

*Nguồn: https://oj.vnoi.info/contest/dsu/all*

Cho một đồ thị gồm n đỉnh, ban đầu chưa có cạnh nào. Đỉnh thứ i có giá trị bằng i.

Có m truy vấn. Truy vấn union u v nối thêm một cạnh giữa hai đỉnh u và v. Truy vấn get u yêu cầu xét thành phần liên thông chứa u và tìm ba thông tin: giá trị đỉnh nhỏ nhất, giá trị đỉnh lớn nhất và số lượng đỉnh trong thành phần đó.

**Yêu cầu:** Với mỗi truy vấn get, in min, max và size của component chứa đỉnh được hỏi.

**Input**

Dòng đầu tiên chứa hai số nguyên dương n và m.  
m dòng tiếp theo, mỗi dòng là một trong hai loại truy vấn:  
\- union u v: nối cạnh giữa u và v.  
\- get u: hỏi thông tin của component chứa u.

**Output**

Với mỗi thao tác get, in ba số nguyên trên một dòng theo thứ tự: giá trị nhỏ nhất, giá trị lớn nhất và kích thước component.

**Giới hạn**

1 \<= n, m \<= 3 \* 10^5.

**Sample Input**

5 11  
union 1 2  
get 3  
get 2  
union 2 3  
get 2  
union 1 3  
get 5  
union 4 5  
get 5  
union 4 1  
get 5

**Sample Output**

3 3 1  
1 2 2  
1 3 3  
5 5 1  
4 5 2  
1 5 5

### **Trò con bò \- VNOJ Educational DSU**

*Nguồn: https://oj.vnoi.info/contest/dsu/all*

Farmer John đang có N chuồng bò, ban đầu các chuồng trống và độc lập với nhau. Trong K lượt, ông đưa ra các hiệu lệnh làm thay đổi quan hệ giữa các chuồng hoặc số bò trong một nhóm chuồng liên thông.

Lệnh join X Y kết nối chuồng X và chuồng Y. Lệnh add X V thêm V con bò cho chuồng X và tất cả chuồng đang kết nối với X. Lệnh get X hỏi số bò hiện có tại chuồng X. Khi hai chuồng đã được nối trực tiếp hoặc gián tiếp qua các chuồng khác, chúng thuộc cùng một nhóm.

**Yêu cầu:** Sau mỗi lệnh get, in số bò hiện có tại chuồng được hỏi.

**Input**

Dòng đầu tiên gồm hai số N và K.  
K dòng tiếp theo là các lệnh của Farmer John:  
\- join X Y  
\- add X V  
\- get X

**Output**

Gồm một số dòng là kết quả của các lệnh get, theo đúng thứ tự xuất hiện.

**Giới hạn**

N \<= 10^5; K \<= 5 \* 10^5; V \<= 100\.

**Sample Input**

4 10  
join 1 2  
add 1 100  
join 2 3  
add 1 50  
join 3 4  
add 1 25  
get 1  
get 2  
get 3  
get 4

**Sample Output**

175  
175  
75  
25

### **Monkeys \- VNOJ Educational DSU**

*Nguồn: https://oj.vnoi.info/contest/dsu/all*

Có n con khỉ đang treo trên một cây rất cao, được đánh số từ 1 đến n. Khỉ số 1 là con đầu đàn và bám vào cây bằng đuôi. Mỗi con khỉ có hai tay; mỗi tay có thể bám vào đuôi của một con khỉ khác, hoặc không bám gì. Một đuôi có thể bị nhiều tay bám vào.

Trong m giây sắp tới, mỗi giây có đúng một con khỉ buông một tay. Khi một con khỉ không còn được nối trực tiếp hoặc gián tiếp với khỉ 1, nó bắt đầu rơi. Cần xác định thời điểm rơi của từng con khỉ.

**Yêu cầu:** In thời điểm mỗi con khỉ bắt đầu rơi, hoặc \-1 nếu nó không bao giờ rơi.

**Input**

Dòng đầu tiên chứa n và m.  
n dòng tiếp theo chứa hai số l\_i, r\_i mô tả tay trái và tay phải của khỉ i đang bám vào đuôi con khỉ nào; giá trị \-1 nghĩa là tay đó không bám vào đuôi nào.  
m dòng tiếp theo mô tả các sự kiện từ giây 0 đến giây m-1. Mỗi dòng gồm p\_j và h\_j; h\_j \= 1 nghĩa là khỉ p\_j buông tay trái, h\_j \= 2 nghĩa là buông tay phải.

**Output**

In n dòng. Dòng thứ i là thời điểm khỉ i bắt đầu rơi; nếu không bao giờ rơi thì in \-1.

**Giới hạn**

1 \<= n \<= 2 \* 10^5; 1 \<= m \<= 4 \* 10^5. Dữ liệu đảm bảo mỗi lần buông tay đều hợp lệ tại thời điểm xảy ra.

**Sample Input 1**

3 2  
\-1 3  
3 \-1  
1 2  
1 2  
3 1

**Sample Output 1**

\-1  
1  
1

**Sample Input 2**

4 5  
2 2  
3 \-1  
4 \-1  
\-1 2  
1 1  
2 1  
3 1  
1 2  
4 2

**Sample Output 2**

\-1  
3  
2  
3

### **Cutting a graph \- VNOJ Educational DSU**

*Nguồn: https://oj.vnoi.info/contest/dsu/all*

Cho một đồ thị vô hướng và một dãy truy vấn. Truy vấn cut u v xóa cạnh u-v khỏi đồ thị; truy vấn ask u v hỏi hai đỉnh u và v hiện có cùng thuộc một thành phần liên thông hay không.

Sau khi thực hiện toàn bộ truy vấn, đồ thị không còn cạnh nào. Vì các cạnh chỉ bị xóa, hướng xử lý tự nhiên là xét truy vấn theo chiều ngược lại: cut trở thành thêm cạnh, còn ask được trả lời tại trạng thái tương ứng.

**Yêu cầu:** Tìm đáp án cho tất cả truy vấn ask theo đúng thứ tự ban đầu.

**Input**

Dòng đầu tiên gồm n, m và k là số đỉnh, số cạnh ban đầu và số truy vấn.  
m dòng tiếp theo mô tả các cạnh ban đầu u\_i v\_i.  
k dòng tiếp theo là truy vấn cut u v hoặc ask u v. Mỗi cạnh ban đầu xuất hiện trong các truy vấn cut đúng một lần.

**Output**

Với mỗi truy vấn ask, in YES nếu hai đỉnh cùng component, ngược lại in NO.

**Giới hạn**

1 \<= n \<= 50000; 0 \<= m \<= 100000; m \<= k \<= 150000\. Đồ thị không có khuyên hoặc cạnh lặp.

**Sample Input**

3 3 7  
1 2  
2 3  
3 1  
ask 3 3  
cut 1 2  
ask 1 2  
cut 1 3  
ask 2 1  
cut 2 3  
ask 3 1

**Sample Output**

YES  
YES  
NO  
NO

### **Mọi người đang rời đi \- VNOJ Educational DSU**

*Nguồn: https://oj.vnoi.info/contest/dsu/all*

Có n người đang đứng ở các vị trí từ 1 đến n. Bạn cần thực hiện hai loại truy vấn.

Truy vấn \- x nghĩa là người ở vị trí x rời đi. Truy vấn ? x yêu cầu tìm người gần nhất vẫn còn đứng ở vị trí không nhỏ hơn x, tức bắt đầu từ x rồi nhìn sang bên phải. Nếu không còn ai phù hợp thì trả về \-1.

**Yêu cầu:** Trả lời mỗi truy vấn ? theo trạng thái hiện tại sau các lần rời đi trước đó.

**Input**

Dòng đầu tiên chứa n và m.  
m dòng tiếp theo, mỗi dòng là một truy vấn:  
\- \- x: người ở vị trí x rời đi.  
\- ? x: hỏi người gần nhất còn đứng từ x sang phải.

**Output**

Với mỗi truy vấn ?, in vị trí tìm được trên một dòng; nếu không có thì in \-1.

**Giới hạn**

1 \<= n, m \<= 10^6. Tất cả vị trí trong các truy vấn rời đi đôi một khác nhau.

**Sample Input**

5 10  
? 1  
\- 3  
? 3  
\- 2  
? 1  
? 2  
\- 4  
? 3  
\- 5  
? 3

**Sample Output**

1  
4  
1  
4  
5  
\-1

### **Parking \- VNOJ Educational DSU**

*Nguồn: https://oj.vnoi.info/contest/dsu/all*

Có n chỗ đỗ xe được sắp theo vòng tròn, đánh số từ 1 đến n theo chiều kim đồng hồ. Có n xe lần lượt đến đỗ; xe đến trước được chọn chỗ trước.

Tại thời điểm i, xe i muốn đỗ ở vị trí p\_i. Nếu vị trí đó đã bị chiếm, xe tiếp tục đi theo chiều kim đồng hồ cho đến khi gặp chỗ trống đầu tiên và đỗ ở đó.

**Yêu cầu:** Xác định chỗ đỗ thực tế của từng xe theo thứ tự xe đến.

**Input**

Dòng đầu tiên chứa số nguyên dương n.  
Dòng thứ hai chứa n số p\_i, trong đó p\_i là vị trí xe i muốn đỗ.

**Output**

In n số nguyên dương; số thứ i là chỉ số chỗ đỗ thực tế của xe i.

**Giới hạn**

1 \<= n \<= 3 \* 10^5; 1 \<= p\_i \<= n.

**Sample Input 1**

3  
2 2 2

**Sample Output 1**

2 3 1

**Sample Input 2**

10  
4 5 3 2 1 1 5 8 9 10

**Sample Output 2**

4 5 3 2 1 6 7 8 9 10

### 

### 

### **Tái cấu trúc công ty \- VNOJ Educational DSU**

*Nguồn: https://oj.vnoi.info/contest/dsu/all*

Một công ty có n nhân viên. Ban đầu, mỗi nhân viên thuộc một phòng ban riêng của chính mình. Khi công ty tái cấu trúc, người quản lý có thể sáp nhập phòng ban của hai nhân viên, hoặc sáp nhập toàn bộ các phòng ban chứa các nhân viên trong một đoạn liên tiếp từ x đến y.

Ngoài các thao tác sáp nhập, người quản lý cũng có các câu hỏi: hai nhân viên x và y có đang làm việc trong cùng một phòng ban hay không.

**Yêu cầu:** Hỗ trợ cả ba loại truy vấn và trả lời chính xác các truy vấn hỏi.

**Input**

Dòng đầu tiên chứa n và q.  
q dòng tiếp theo, mỗi dòng có dạng type x y:  
\- type \= 1: gộp phòng ban chứa x và phòng ban chứa y.  
\- type \= 2: gộp các phòng ban chứa x, x+1, ..., y.  
\- type \= 3: hỏi x và y có cùng phòng ban hay không.

**Output**

Với mỗi truy vấn type \= 3, in YES nếu hai nhân viên cùng phòng ban, ngược lại in NO.

**Giới hạn**

1 \<= n \<= 200000; 1 \<= q \<= 500000; 1 \<= type \<= 3; x có thể bằng y.

**Sample Input 1**

8 6  
3 2 5  
1 2 5  
3 2 5  
2 4 7  
2 1 2  
3 1 7

**Sample Output 1**

NO  
YES  
YES

**Sample Input 2**

7 7  
1 2 3  
2 4 6  
3 5 6  
3 4 3  
2 1 2  
1 6 3  
3 6 1

**Sample Output 2**

YES  
NO  
YES

### **Bosses \- VNOJ Educational DSU**

*Nguồn: https://oj.vnoi.info/contest/dsu/all*

Cho một rừng gồm n đỉnh. Ban đầu không có cạnh nào và mỗi đỉnh là gốc của chính nó.

Có hai loại truy vấn. Truy vấn loại 1 cho gốc a trở thành con của gốc b, khiến a không còn là gốc nữa. Truy vấn loại 2 cho một node c và yêu cầu in khoảng cách từ c đến gốc hiện tại của cây chứa c. Nếu c là gốc thì khoảng cách bằng 0\.

**Yêu cầu:** Sau mỗi truy vấn loại 2, in độ sâu hiện tại của node được hỏi so với gốc của nó.

**Input**

Dòng đầu chứa n và m.  
m dòng tiếp theo là truy vấn:  
\- 1 a b: gốc a trở thành con của gốc b; a và b là hai gốc khác nhau.  
\- 2 c: hỏi khoảng cách từ c đến gốc hiện tại.

**Output**

Với mỗi truy vấn loại 2, in kết quả trên một dòng.

**Giới hạn**

1 \<= n, m \<= 3 \* 10^5; trong truy vấn loại 1, a \!= b và cả hai đang là gốc.

**Sample Input**

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

**Sample Output**

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

### **Number of Provinces \- LeetCode**

*Nguồn: https://leetcode.com/problems/number-of-provinces/description/*

Có n thành phố. Một số cặp thành phố được nối trực tiếp, một số thì không. Nếu a nối trực tiếp với b và b nối trực tiếp với c, thì a và c được xem là nối gián tiếp.

Một province là một nhóm thành phố liên thông trực tiếp hoặc gián tiếp với nhau, và không liên thông với thành phố nào bên ngoài nhóm. Cho ma trận isConnected kích thước n x n mô tả quan hệ nối trực tiếp giữa các thành phố.

**Yêu cầu:** Trả về tổng số province trong ma trận đã cho.

**Input**

Hàm nhận ma trận isConnected, trong đó isConnected\[i\]\[j\] \= 1 nếu thành phố i và j nối trực tiếp, ngược lại bằng 0\.

**Output**

Trả về một số nguyên là số province.

**Giới hạn**

1 \<= n \<= 200; n \== isConnected.length; isConnected là ma trận n x n; các phần tử là 0 hoặc 1; ma trận đối xứng và đường chéo chính bằng 1\.

**Sample Input 1**

isConnected \= \[\[1,1,0\],\[1,1,0\],\[0,0,1\]\]

**Sample Output 1**

2

**Sample Input 2**

isConnected \= \[\[1,0,0\],\[0,1,0\],\[0,0,1\]\]

**Sample Output 2**

3

### 

### 

### **News Distribution \- Codeforces 1167C**

*Nguồn: https://codeforces.com/problemset/problem/1167/C*

Trong một mạng xã hội có n người dùng và m nhóm bạn. Hai người được xem là bạn nếu tồn tại ít nhất một nhóm mà cả hai cùng thuộc về.

Ban đầu chỉ có một người x nhận được tin. Người đó gửi tin cho bạn bè của mình, rồi những người mới biết tin tiếp tục gửi cho bạn bè của họ. Quá trình dừng khi không còn cặp bạn bè nào mà một người biết tin và người kia chưa biết. Với mỗi người x, cần biết nếu x là người bắt đầu thì cuối cùng có bao nhiêu người biết tin.

**Yêu cầu:** In kích thước component bạn bè của từng người dùng.

**Input**

Dòng đầu tiên chứa n và m.  
m dòng tiếp theo mô tả các nhóm. Dòng thứ i bắt đầu bằng k\_i, sau đó là k\_i số nguyên phân biệt chỉ những người thuộc nhóm i. Nhóm có thể rỗng.

**Output**

In n số nguyên. Số thứ i là số người sẽ biết tin nếu người i là người bắt đầu phát tin.

**Giới hạn**

1 \<= n, m \<= 5 \* 10^5; 0 \<= k\_i \<= n; tổng tất cả k\_i không vượt quá 5 \* 10^5.

**Sample Input**

7 5  
3 2 5 4  
0  
2 1 2  
1 1  
2 6 7

**Sample Output**

4 4 1 4 4 2 2

### **Merging Communities \- HackerRank**

*Nguồn: https://www.hackerrank.com/challenges/merging-communities/problem*

Trong một mạng xã hội, mỗi người ban đầu là một cộng đồng riêng. Khi hai người thuộc hai cộng đồng khác nhau kết nối với nhau, toàn bộ hai cộng đồng được gộp lại thành một cộng đồng mới.

Có hai loại truy vấn: M i j để gộp cộng đồng chứa i và cộng đồng chứa j, và Q i để hỏi kích thước cộng đồng hiện tại của người i.

**Yêu cầu:** Xử lý các thao tác gộp và in kích thước cộng đồng cho mỗi truy vấn Q.

**Input**

Dòng đầu tiên chứa n và q là số người và số truy vấn.  
q dòng tiếp theo là truy vấn:  
\- M i j: gộp hai cộng đồng chứa i và j nếu khác nhau.  
\- Q i: hỏi kích thước cộng đồng chứa i.

**Output**

Với mỗi truy vấn Q, in một dòng là kích thước cộng đồng tương ứng.

**Giới hạn**

Các chỉ số người trong khoảng 1..n; chuỗi truy vấn hợp lệ theo đề HackerRank.

**Sample Input**

3 6  
Q 1  
M 1 2  
Q 2  
M 2 3  
Q 3  
Q 2

**Sample Output**

1  
2  
3  
3

# **Stack / Queue**

### **SSAM219G \- SPOJ PTIT**

*Nguồn: https://www.spoj.com/PTIT/problems/SSAM219G/*

**Ghi chú:** Không truy xuất được nội dung đề chính thức trong phiên này vì SPOJ trả Cloudflare challenge/403 cho request tự động. Mục này được giữ lại theo danh sách link để bạn bổ sung khi mở được bằng trình duyệt.

Chưa truy xuất được statement chính thức trong phiên này vì trang SPOJ trả Cloudflare challenge/403 cho request tự động. Mục này được giữ trong tài liệu theo danh sách bài bạn đưa để dễ bổ sung sau khi mở được nội dung gốc.

**Yêu cầu:** Chưa xác nhận được yêu cầu chính thức từ nguồn.

**Input**

Chưa xác nhận được input format chính thức từ nguồn.

**Output**

Chưa xác nhận được output format chính thức từ nguồn.

**Giới hạn**

Chưa xác nhận được giới hạn chính thức từ nguồn.

### 

### 

### 

### **Queue using Two Stacks \- HackerRank**

*Nguồn: https://www.hackerrank.com/challenges/queue-using-two-stacks/problem*

Queue là cấu trúc FIFO: phần tử được thêm vào sau hàng đợi, còn phần tử ở đầu hàng đợi được lấy ra trước. Bài yêu cầu cài đặt queue bằng hai stack.

Sau khi cài đặt, cần xử lý q truy vấn gồm thêm phần tử vào cuối queue, xóa phần tử ở đầu queue, hoặc in phần tử đang ở đầu queue.

**Yêu cầu:** Mô phỏng đúng hàng đợi FIFO chỉ bằng hai stack.

**Input**

Dòng đầu tiên chứa q là số truy vấn.  
Mỗi dòng tiếp theo là một truy vấn:  
\- 1 x: enqueue x vào cuối queue.  
\- 2: dequeue phần tử ở đầu queue.  
\- 3: in phần tử ở đầu queue.

**Output**

Với mỗi truy vấn loại 3, in giá trị ở đầu queue trên một dòng mới.

**Giới hạn**

Có q truy vấn; mỗi truy vấn loại 3 luôn có đáp án hợp lệ.

**Sample Input**

10  
1 42  
2  
1 14  
3  
1 28  
3  
1 60  
1 78  
2  
2

**Sample Output**

14  
14

### **Simple Text Editor \- HackerRank**

*Nguồn: https://www.hackerrank.com/challenges/simple-text-editor/problem*

Cần cài đặt một trình soạn thảo văn bản đơn giản. Ban đầu chuỗi S rỗng.

Có bốn thao tác: append W để thêm W vào cuối S, delete k để xóa k ký tự cuối của S, print k để in ký tự thứ k của S, và undo để hoàn tác thao tác append/delete gần nhất chưa bị undo. Thao tác undo đưa S về trạng thái trước thao tác cập nhật đó.

**Yêu cầu:** Mô phỏng chính xác các thao tác và in kết quả cho mỗi thao tác print.

**Input**

Dòng đầu tiên chứa Q là số thao tác.  
Q dòng tiếp theo mô tả thao tác:  
\- 1 W: append W vào cuối chuỗi.  
\- 2 k: xóa k ký tự cuối.  
\- 3 k: in ký tự thứ k.  
\- 4: undo thao tác cập nhật gần nhất.

**Output**

Mỗi thao tác loại 3 in một ký tự trên một dòng.

**Giới hạn**

Chuỗi chỉ gồm chữ thường tiếng Anh. Dữ liệu đảm bảo mọi thao tác đều thực hiện được. Tổng độ dài các chuỗi append và tổng số ký tự bị delete được giới hạn theo đề HackerRank.

**Sample Input**

8  
1 abc  
3 3  
2 3  
1 xy  
3 2  
4  
4  
3 1

**Sample Output**

c  
y  
a

### **Largest Rectangle in Histogram \- LeetCode**

*Nguồn: https://leetcode.com/problems/largest-rectangle-in-histogram/*

Cho mảng số nguyên heights biểu diễn chiều cao các cột trong một histogram. Mỗi cột có chiều rộng bằng 1\.

Cần tìm hình chữ nhật có diện tích lớn nhất có thể nằm hoàn toàn trong histogram. Một hình chữ nhật có thể trải qua nhiều cột liên tiếp, với chiều cao không vượt quá cột thấp nhất trong đoạn đó.

**Yêu cầu:** Trả về diện tích lớn nhất của một hình chữ nhật trong histogram.

**Input**

Hàm nhận mảng số nguyên heights.

**Output**

Trả về một số nguyên là diện tích lớn nhất.

**Giới hạn**

1 \<= heights.length \<= 10^5; 0 \<= heights\[i\] \<= 10^4.

**Sample Input 1**

heights \= \[2,1,5,6,2,3\]

**Sample Output 1**

10

**Sample Input 2**

heights \= \[2,4\]

**Sample Output 2**

4

# **Sort / Two Pointers**

### **3Sum Closest \- LeetCode**

*Nguồn: https://leetcode.com/problems/3sum-closest/description/?envType=problem-list-v2\&envId=sorting*

Cho mảng số nguyên nums độ dài n và một số nguyên target. Cần chọn ba phần tử ở ba chỉ số khác nhau sao cho tổng của chúng gần target nhất.

Đề đảm bảo mỗi test có đúng một đáp án theo tiêu chí gần nhất.

**Yêu cầu:** Trả về tổng của bộ ba phần tử có tổng gần target nhất.

**Input**

Hàm nhận mảng nums và số nguyên target.

**Output**

Trả về một số nguyên là tổng của ba phần tử được chọn.

**Giới hạn**

3 \<= nums.length \<= 500; \-1000 \<= nums\[i\] \<= 1000; \-10^4 \<= target \<= 10^4.

**Sample Input 1**

nums \= \[-1,2,1,-4\], target \= 1

**Sample Output 1**

2

**Sample Input 2**

nums \= \[0,0,0\], target \= 1

**Sample Output 2**

0

### 

### 

### **Double Sort \- Codeforces 1681C**

*Nguồn: https://codeforces.com/contest/1681/problem/C*

Cho hai mảng a và b cùng có n phần tử. Một thao tác cho phép chọn hai chỉ số i và j, sau đó hoán đổi a\_i với a\_j đồng thời hoán đổi b\_i với b\_j. Nói cách khác, mỗi vị trí có thể xem như một cặp (a\_i, b\_i), và thao tác sẽ đổi chỗ hai cặp.

Mục tiêu là làm cho cả hai mảng a và b đều không giảm sau một số thao tác. Được phép thực hiện nhiều nhất 10^4 thao tác. Nếu có nhiều cách làm hợp lệ, có thể in bất kỳ cách nào; không cần tối ưu số thao tác.

Bản chất sort của bài là sắp xếp các cặp theo a rồi theo b, hoặc mô phỏng hoán vị bằng pair/struct. Sau khi sắp xếp, cần kiểm tra lại cả hai dãy đều không giảm. Với n \<= 100, cách chọn sort O(n^2) và ghi lại swap là đủ an toàn.

**Yêu cầu:** Với mỗi test case, in \-1 nếu không thể làm cả hai mảng không giảm; nếu có thể, in một dãy thao tác hoán đổi hợp lệ không quá 10^4 thao tác.

**Input**

Dòng đầu tiên chứa t là số test case.  
Mỗi test case gồm:  
\- Dòng đầu chứa n là số phần tử của hai mảng.  
\- Dòng thứ hai chứa n số a\_1, a\_2, ..., a\_n.  
\- Dòng thứ ba chứa n số b\_1, b\_2, ..., b\_n.

**Output**

Với mỗi test case:  
\- Nếu không thể, in \-1.  
\- Nếu có thể, trước tiên in số thao tác k, với 0 \<= k \<= 10^4. Sau đó in k dòng, mỗi dòng gồm hai chỉ số i và j là hai vị trí được hoán đổi đồng thời ở cả hai mảng.

Nếu có nhiều đáp án đúng, in bất kỳ đáp án nào.

**Giới hạn**

1 \<= t \<= 100; 2 \<= n \<= 100; 1 \<= a\_i, b\_i \<= n; giới hạn thời gian 2s, bộ nhớ 256MB.

**Sample Input**

3  
2  
1 2  
1 2  
2  
2 1  
1 2  
4  
2 3 1 2  
2 3 2 3

**Sample Output**

0  
\-1  
3  
3 1  
3 2  
4 3

### **Movie Festival \- CSES 1629**

*Nguồn: https://cses.fi/problemset/task/1629*

Trong một liên hoan phim có n bộ phim được chiếu. Bạn biết thời gian bắt đầu và thời gian kết thúc của từng bộ phim.

Bạn chỉ có thể xem trọn vẹn một bộ phim nếu không bị chồng lấn với bộ phim đã chọn trước đó. Mục tiêu là chọn được số lượng phim nhiều nhất có thể xem đầy đủ.

Bản chất của bài là sort \+ tham lam: sắp xếp các phim theo thời điểm kết thúc tăng dần, sau đó luôn chọn bộ phim kết thúc sớm nhất trong số các phim có thể xem tiếp theo.

**Yêu cầu:** In số lượng phim tối đa có thể xem trọn vẹn.

**Input**

Dòng đầu tiên chứa n là số bộ phim.  
n dòng tiếp theo, mỗi dòng chứa hai số a và b là thời gian bắt đầu và kết thúc của một bộ phim.

**Output**

In một số nguyên duy nhất: số bộ phim tối đa có thể xem.

**Giới hạn**

1 \<= n \<= 2 \* 10^5; 1 \<= a \< b \<= 10^9; giới hạn thời gian 1s, bộ nhớ 512MB.

**Sample Input**

3  
3 5  
4 9  
5 8

**Sample Output**

2