-- Tạo bảng và insert data mẫu
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

INSERT INTO users (name, email) VALUES
    ('Nguyen Van A', 'a@example.com'),
    ('Tran Thi B', 'b@example.com'),
    ('Le Van C', 'c@example.com');
