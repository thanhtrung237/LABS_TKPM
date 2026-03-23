package com.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderId implements Serializable {
    private Long id;
    private LocalDateTime createdAt;
}
