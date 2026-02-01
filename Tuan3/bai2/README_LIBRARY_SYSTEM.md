# Hệ Thống Quản Lý Thư Viện - Design Patterns Demo

## Tổng quan
Hệ thống quản lý thư viện được xây dựng sử dụng 5 Design Patterns chính:

### 1. Singleton Pattern
- **Class**: `Library`
- **Mục đích**: Đảm bảo chỉ có một instance duy nhất của thư viện trong hệ thống
- **Sử dụng**: `Library.getInstance()`

### 2. Factory Method Pattern
- **Classes**: `BookFactory`, `PhysicalBookFactory`, `EBookFactory`, `AudioBookFactory`
- **Mục đích**: Tạo ra các loại sách khác nhau (sách giấy, e-book, audiobook)
- **Sử dụng**: `BookFactory.getFactory("physical").createBook(...)`

### 3. Strategy Pattern
- **Classes**: `SearchStrategy`, `SearchByTitle`, `SearchByAuthor`, `SearchByGenre`
- **Mục đích**: Thay đổi chiến lược tìm kiếm sách linh hoạt
- **Sử dụng**: `library.setSearchStrategy(new SearchByTitle())`

### 4. Observer Pattern
- **Classes**: `LibraryObserver`, `LibrarianObserver`, `UserObserver`
- **Mục đích**: Thông báo khi có sự kiện xảy ra (thêm sách, mượn sách, trả sách)
- **Sử dụng**: `library.addObserver(new LibrarianObserver("Alice"))`

### 5. Decorator Pattern
- **Classes**: `BookRental`, `BasicBookRental`, `ExtendedRentalDecorator`, `BrailleEditionDecorator`, `TranslatedEditionDecorator`
- **Mục đích**: Thêm tính năng bổ sung cho việc mượn sách mà không thay đổi class gốc
- **Sử dụng**: `new ExtendedRentalDecorator(rental, 7)`

## Cách chạy ứng dụng

### 1. Chạy ứng dụng Spring Boot
```bash
./gradlew bootRun
```

### 2. Demo sẽ tự động chạy và hiển thị các Design Patterns

### 3. Sử dụng REST API

#### Lấy tất cả sách:
```
GET http://localhost:8080/api/library/books
```

#### Lấy sách có sẵn:
```
GET http://localhost:8080/api/library/books/available
```

#### Tìm kiếm sách theo tên:
```
GET http://localhost:8080/api/library/books/search/title?title=Hobbit
```

#### Tìm kiếm sách theo tác giả:
```
GET http://localhost:8080/api/library/books/search/author?author=Tolkien
```

#### Tìm kiếm sách theo thể loại:
```
GET http://localhost:8080/api/library/books/search/genre?genre=Fiction
```

#### Thêm sách mới:
```
POST http://localhost:8080/api/library/books
Content-Type: application/json

{
    "type": "physical",
    "id": "P003",
    "title": "New Book",
    "author": "Author Name",
    "genre": "Genre",
    "params": ["A1-03", 250]
}
```

#### Mượn sách cơ bản:
```
POST http://localhost:8080/api/library/books/P001/borrow?borrower=john@email.com
```

#### Mượn sách với gia hạn:
```
POST http://localhost:8080/api/library/books/P001/borrow/extended?borrower=john@email.com&extraDays=7
```

#### Mượn sách phiên bản Braille:
```
POST http://localhost:8080/api/library/books/P001/borrow/braille?borrower=john@email.com
```

#### Mượn sách có bản dịch:
```
POST http://localhost:8080/api/library/books/P001/borrow/translated?borrower=john@email.com&language=Vietnamese
```

#### Trả sách:
```
POST http://localhost:8080/api/library/books/P001/return
```

#### Xem thông tin mượn sách:
```
GET http://localhost:8080/api/library/rentals/P001
```

#### Xem tất cả sách đang được mượn:
```
GET http://localhost:8080/api/library/rentals
```

## Cấu trúc dự án

```
src/main/java/iuh/fit/bai2/
├── controller/          # REST Controllers
├── decorator/           # Decorator Pattern
├── factory/            # Factory Method Pattern
├── model/              # Book models
├── observer/           # Observer Pattern
├── service/            # Business logic
├── singleton/          # Singleton Pattern
├── strategy/           # Strategy Pattern
└── Bai2Application.java # Main application
```

## Tính năng chính

1. **Quản lý sách**: Thêm, xem, tìm kiếm sách
2. **Mượn/Trả sách**: Với các tính năng bổ sung
3. **Thông báo**: Tự động thông báo khi có sự kiện
4. **Tìm kiếm linh hoạt**: Theo tên, tác giả, thể loại
5. **Mở rộng tính năng**: Gia hạn, Braille, bản dịch

## Lợi ích của Design Patterns

- **Singleton**: Đảm bảo tính nhất quán của dữ liệu thư viện
- **Factory Method**: Dễ dàng thêm loại sách mới
- **Strategy**: Linh hoạt trong việc thay đổi cách tìm kiếm
- **Observer**: Tự động hóa thông báo
- **Decorator**: Mở rộng tính năng mà không thay đổi code cũ