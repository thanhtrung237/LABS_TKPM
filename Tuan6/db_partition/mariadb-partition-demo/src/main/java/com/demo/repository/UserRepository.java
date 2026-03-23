package com.demo.repository;

import com.demo.entity.User;
import com.demo.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, UserId> {

    // Horizontal: query chỉ nam -> MariaDB tự scan partition table_user_01
    List<User> findByGenderId(Integer genderId);

    // Native query để xem partition nào được dùng
    @Query(value = "SELECT * FROM users PARTITION (table_user_01)", nativeQuery = true)
    List<User> findAllMaleFromPartition();

    @Query(value = "SELECT * FROM users PARTITION (table_user_02)", nativeQuery = true)
    List<User> findAllFemaleFromPartition();
}
