package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * VERTICAL PARTITION: chỉ chứa cột hay dùng.
 * Cột nặng (avatar, bio) nằm ở UserProfileDetail.
 */
@Data
@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String gender;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Lazy load -> chỉ fetch khi thực sự cần
    @OneToOne(mappedBy = "userProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private UserProfileDetail detail;
}
