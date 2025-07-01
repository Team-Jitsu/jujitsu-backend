package com.fightingkorea.platform.domain.user.controller;

import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import com.fightingkorea.platform.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;


    /**
     * 전체 유저 목록을 조회하는 메서드.
     *
     * @param name
     * @param sex
     * @param fromDate
     * @param toDate
     * @param pageable
     * @return
     */
    @GetMapping
    public Page<User> getUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Sex sex,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate,
            Pageable pageable) {
        return userService.getUsers(name, sex, role, fromDate, toDate, pageable);
    }

    /**
     * 특정 유저의 정보를 조회하는 메서드입니다.
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public UserResponse getUserInfo(@PathVariable Long userId){
        UserResponse userResponse = userService.getUserInfo(userId);
        return userResponse;
    }

    /**
     * 관리자가 특정 유저를 삭제합니다.
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

    /**
     * 관리자가 특정 유저의 상태를 변경합니다.(활성화/비활성화)
     * @param userId
     * @param isActive
     * @return
     */
    @PutMapping("/{userId}")
    public UserResponse updateUserActive(Long userId, Boolean isActive){
        UserResponse userResponse = userService.updateUserActive(userId, isActive);

        return userResponse;
    }

}
