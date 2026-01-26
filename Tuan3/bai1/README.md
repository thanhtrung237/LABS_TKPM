# Design Patterns Implementation

Dự án này triển khai 3 Design Patterns chính với các ví dụ thực tế:

## 🏗️ 1. Composite Pattern - File System Management

**Mục đích**: Quản lý hệ thống thư mục và tập tin theo cấu trúc cây

**Cấu trúc**:
- `FileSystemComponent` - Abstract component
- `File` - Leaf component (tập tin)
- `Directory` - Composite component (thư mục)

**Ứng dụng**:
- Quản lý file system
- Hiển thị cấu trúc cây thư mục
- Tính toán dung lượng tổng

## 👀 2. Observer Pattern - Notification System

**Mục đích**: Thông báo tự động khi có thay đổi

**Cấu trúc**:
- `Subject` - Interface cho đối tượng được quan sát
- `Observer` - Interface cho đối tượng quan sát
- `StockPrice` - Theo dõi giá cổ phiếu
- `TaskStatus` - Theo dõi trạng thái công việc
- `Investor`, `TeamMember` - Các observer cụ thể

**Ứng dụng**:
- Hệ thống thông báo giá cổ phiếu
- Theo dõi trạng thái task trong dự án

## 🔌 3. Adapter Pattern - Data Format Conversion

**Mục đích**: Chuyển đổi giữa các định dạng dữ liệu không tương thích

**Cấu trúc**:
- `DataProcessor` - Target interface (JSON)
- `JSONDataProcessor` - Xử lý JSON
- `XMLDataProcessor` - Adaptee (chỉ xử lý XML)
- `XMLToJSONAdapter` - Adapter chuyển đổi XML sang JSON

**Ứng dụng**:
- Tích hợp hệ thống legacy (XML) với hệ thống mới (JSON)
- Web service data conversion

## 🚀 Cách chạy

```bash
# Build project
./gradlew build

# Run application
./gradlew bootRun
```

## 📁 Cấu trúc dự án

```
src/main/java/iuh/fit/bai1/
├── composite/          # Composite Pattern
│   ├── FileSystemComponent.java
│   ├── File.java
│   └── Directory.java
├── observer/           # Observer Pattern
│   ├── Subject.java
│   ├── Observer.java
│   ├── StockPrice.java
│   ├── TaskStatus.java
│   ├── Investor.java
│   └── TeamMember.java
├── adapter/            # Adapter Pattern
│   ├── DataProcessor.java
│   ├── JSONDataProcessor.java
│   ├── XMLDataProcessor.java
│   └── XMLToJSONAdapter.java
├── demo/               # Demo classes
│   ├── CompositePatternDemo.java
│   ├── ObserverPatternDemo.java
│   └── AdapterPatternDemo.java
└── Bai1Application.java
```

## 📊 Sơ đồ UML

Xem các file trong thư mục `diagrams/` để hiểu rõ cấu trúc của từng pattern:
- `composite-pattern.md`
- `observer-pattern.md`
- `adapter-pattern.md`

## 🎯 Kết quả mong đợi

Khi chạy ứng dụng, bạn sẽ thấy:
1. Demo Composite Pattern với file system
2. Demo Observer Pattern với stock prices và task status
3. Demo Adapter Pattern với JSON/XML conversion

Mỗi demo sẽ hiển thị cách pattern hoạt động trong thực tế với output chi tiết.