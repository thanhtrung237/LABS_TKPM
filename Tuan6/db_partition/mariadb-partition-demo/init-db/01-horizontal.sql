-- =============================================
-- HORIZONTAL PARTITIONING
-- Chia bảng user theo giới tính: nam -> table_user_01, nữ -> table_user_02
-- Dùng PARTITION BY LIST trên cột gender_id
-- =============================================

USE partitiondb;

CREATE TABLE users (
    id       BIGINT       NOT NULL,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100),
    gender   VARCHAR(10)  NOT NULL,  -- 'male' | 'female'
    gender_id TINYINT     NOT NULL,  -- 1 = male, 2 = female
    created_at DATETIME DEFAULT NOW(),
    PRIMARY KEY (id, gender_id)      -- partition key phải nằm trong PK
)
PARTITION BY LIST (gender_id) (
    PARTITION table_user_01 VALUES IN (1),   -- nam
    PARTITION table_user_02 VALUES IN (2)    -- nữ
);

-- Seed data
INSERT INTO users (id, name, email, gender, gender_id) VALUES
(1, 'Nguyen Van A', 'a@mail.com', 'male',   1),
(2, 'Tran Thi B',  'b@mail.com', 'female',  2),
(3, 'Le Van C',    'c@mail.com', 'male',    1),
(4, 'Pham Thi D',  'd@mail.com', 'female',  2);

-- Kiểm tra dữ liệu từng partition
-- SELECT * FROM users PARTITION (table_user_01);  -- chỉ nam
-- SELECT * FROM users PARTITION (table_user_02);  -- chỉ nữ
