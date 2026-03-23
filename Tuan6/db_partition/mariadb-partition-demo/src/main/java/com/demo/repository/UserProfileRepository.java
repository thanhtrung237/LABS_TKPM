package com.demo.repository;

import com.demo.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    // Vertical: chỉ lấy cột nhẹ, không JOIN detail -> nhanh
    @Query("SELECT p FROM UserProfile p")
    List<UserProfile> findAllLightweight();

    // Khi cần full info: fetch join với detail
    @Query("SELECT p FROM UserProfile p LEFT JOIN FETCH p.detail WHERE p.id = :id")
    UserProfile findByIdWithDetail(Long id);
}
