package com.demo.controller;

import com.demo.entity.*;
import com.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PartitionDemoController {

    private final UserRepository userRepo;
    private final UserProfileRepository profileRepo;
    private final OrderRepository orderRepo;

    // ── HORIZONTAL ──────────────────────────────────────────────
    // GET /api/users/male  -> chỉ scan partition table_user_01
    @GetMapping("/users/male")
    public List<User> getMale() {
        return userRepo.findAllMaleFromPartition();
    }

    // GET /api/users/female -> chỉ scan partition table_user_02
    @GetMapping("/users/female")
    public List<User> getFemale() {
        return userRepo.findAllFemaleFromPartition();
    }

    // ── VERTICAL ────────────────────────────────────────────────
    // GET /api/profiles       -> chỉ lấy cột nhẹ (không load avatar/bio)
    @GetMapping("/profiles")
    public List<UserProfile> getProfiles() {
        return profileRepo.findAllLightweight();
    }

    // GET /api/profiles/{id}  -> full info kèm detail
    @GetMapping("/profiles/{id}")
    public UserProfile getProfileDetail(@PathVariable Long id) {
        return profileRepo.findByIdWithDetail(id);
    }

    // ── FUNCTION-BASED ──────────────────────────────────────────
    // GET /api/orders?year=2024 -> chỉ scan partition p2024
    @GetMapping("/orders")
    public List<Order> getOrdersByYear(@RequestParam int year) {
        LocalDateTime from = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime to   = LocalDateTime.of(year, 12, 31, 23, 59);
        return orderRepo.findByCreatedAtBetween(from, to);
    }

    // GET /api/orders/partitions -> xem số row mỗi partition
    @GetMapping("/orders/partitions")
    public List<Map<String, Object>> getPartitionInfo() {
        return orderRepo.getPartitionInfo().stream()
            .map(row -> Map.of(
                "partition", row[0],
                "rows",      row[1]
            ))
            .toList();
    }
}
