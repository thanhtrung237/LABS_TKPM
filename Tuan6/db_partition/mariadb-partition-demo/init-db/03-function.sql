-- =============================================
-- FUNCTION-BASED PARTITIONING (RANGE / HASH)
-- Chia bảng orders theo năm tạo đơn hàng
-- -> query theo khoảng thời gian chỉ scan đúng partition
-- =============================================

USE partitiondb;

CREATE TABLE orders (
    id         BIGINT    NOT NULL,
    user_id    BIGINT    NOT NULL,
    amount     DECIMAL(15,2),
    status     VARCHAR(20),
    created_at DATETIME  NOT NULL DEFAULT NOW(),
    -- YEAR() là function dùng để partition
    PRIMARY KEY (id, created_at)
)
PARTITION BY RANGE (YEAR(created_at)) (
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p_future VALUES LESS THAN MAXVALUE   -- catch-all
);

-- Seed data
INSERT INTO orders (id, user_id, amount, status, created_at) VALUES
(1, 1, 150000, 'PAID',    '2023-06-15 10:00:00'),
(2, 2, 320000, 'PAID',    '2024-01-20 09:30:00'),
(3, 1, 99000,  'PENDING', '2024-11-05 14:00:00'),
(4, 2, 450000, 'PAID',    '2025-03-01 08:00:00');

-- Query chỉ scan partition p2024 (pruning tự động)
-- EXPLAIN SELECT * FROM orders WHERE created_at BETWEEN '2024-01-01' AND '2024-12-31';

-- Xem partition nào chứa data
-- SELECT PARTITION_NAME, TABLE_ROWS
-- FROM information_schema.PARTITIONS
-- WHERE TABLE_NAME = 'orders' AND TABLE_SCHEMA = 'partitiondb';
