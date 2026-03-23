package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * FUNCTION-BASED PARTITION: bảng orders chia theo YEAR(created_at).
 * Query có WHERE created_at BETWEEN ... sẽ chỉ scan đúng partition (partition pruning).
 */
@Data
@Entity
@Table(name = "orders")
@IdClass(OrderId.class)
public class Order {

    @Id
    private Long id;

    @Id
    @Column(name = "created_at")
    private LocalDateTime createdAt;    // partition key

    @Column(name = "user_id")
    private Long userId;

    private BigDecimal amount;
    private String status;
}
