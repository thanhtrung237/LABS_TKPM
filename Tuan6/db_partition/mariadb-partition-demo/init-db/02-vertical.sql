-- =============================================
-- VERTICAL PARTITIONING
-- Tách cột ít dùng (avatar, bio) ra bảng riêng
-- Truy vấn thông thường chỉ join bảng nhỏ -> nhanh hơn
-- =============================================

USE partitiondb;

-- Bảng chính: thông tin hay dùng
CREATE TABLE user_profile (
    id       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL,
    gender   VARCHAR(10),
    created_at DATETIME DEFAULT NOW()
);

-- Bảng phụ: thông tin ít dùng (heavy columns)
CREATE TABLE user_profile_detail (
    user_id  BIGINT       NOT NULL PRIMARY KEY,
    avatar   LONGBLOB,                -- ảnh lớn
    bio      TEXT,
    address  VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user_profile(id)
);

-- Seed data
INSERT INTO user_profile (name, email, gender) VALUES
('Nguyen Van A', 'a@mail.com', 'male'),
('Tran Thi B',   'b@mail.com', 'female');

INSERT INTO user_profile_detail (user_id, bio, address) VALUES
(1, 'Developer at HCM', 'Ho Chi Minh City'),
(2, 'Designer at HN',   'Ha Noi');

-- Query thông thường: chỉ đọc bảng nhỏ, không load BLOB
-- SELECT id, name, email FROM user_profile WHERE id = 1;

-- Query khi cần full info: join thêm
-- SELECT p.*, d.bio, d.address
-- FROM user_profile p
-- LEFT JOIN user_profile_detail d ON p.id = d.user_id
-- WHERE p.id = 1;
