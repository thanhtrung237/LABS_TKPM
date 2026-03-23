package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * VERTICAL PARTITION: chứa cột ít dùng / nặng.
 */
@Data
@Entity
@Table(name = "user_profile_detail")
public class UserProfileDetail {

    @Id
    @Column(name = "user_id")
    private Long userId;

    private byte[] avatar;  // LONGBLOB
    private String bio;
    private String address;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private UserProfile userProfile;
}
