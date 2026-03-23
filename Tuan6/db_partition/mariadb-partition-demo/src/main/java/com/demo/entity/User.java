package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Map tới bảng users có HORIZONTAL PARTITION theo gender_id.
 * MariaDB tự route INSERT/SELECT đúng partition (table_user_01 hoặc table_user_02).
 */
@Data
@Entity
@Table(name = "users")
@IdClass(UserId.class)          // composite PK: id + gender_id
public class User {

    @Id
    private Long id;

    @Id
    @Column(name = "gender_id")
    private Integer genderId;   // 1 = male (table_user_01), 2 = female (table_user_02)

    private String name;
    private String email;
    private String gender;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
