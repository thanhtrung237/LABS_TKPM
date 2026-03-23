package com.demo.repository;

import com.demo.entity.Order;
import com.demo.entity.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, OrderId> {

    // Function-based: MariaDB tự pruning -> chỉ scan partition của năm tương ứng
    List<Order> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    // Xem partition info
    @Query(value = """
        SELECT PARTITION_NAME, TABLE_ROWS
        FROM information_schema.PARTITIONS
        WHERE TABLE_NAME = 'orders' AND TABLE_SCHEMA = 'partitiondb'
        """, nativeQuery = true)
    List<Object[]> getPartitionInfo();
}
