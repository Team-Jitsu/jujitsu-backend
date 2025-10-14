package com.fightingkorea.platform.global;

import com.fightingkorea.platform.domain.earning.service.EarningService;
import com.fightingkorea.platform.domain.admin.dto.*;
import com.fightingkorea.platform.domain.admin.service.AdminService;
import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import com.fightingkorea.platform.domain.trainer.service.TrainerService;
import com.fightingkorea.platform.domain.user.dto.*;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import com.fightingkorea.platform.domain.user.service.UserService;
import com.fightingkorea.platform.global.common.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final TrainerService trainerService;
    private final EarningService earningService;
    private final AdminService adminService;

    // 강의 수정
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/videos/{videoId}")
    public void updateAdminVideo(@PathVariable Long videoId, @RequestBody Map<String, Object> updateData) {
        adminService.updateAdminVideo(videoId, updateData);
    }

    // 강의 삭제
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/videos/{videoId}")
    public void deleteAdminVideo(@PathVariable Long videoId) {
        adminService.deleteAdminVideo(videoId);
    }

    /**
     * 관리자가 회원을 생성하는 메서드.
     *
     * @param userRegisterRequest 회원 생성 요청
     * @param role                회원 권한
     * @return 생성된 회원 정보
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody @Validated UserRegisterRequest userRegisterRequest,
            @RequestParam Role role) {
        UserResponse userResponse = userService.registerUser(userRegisterRequest, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public Page<UserResponse> getUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Sex sex,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate,
            Pageable pageable) {
        return userService.getUsers(name, sex, role, fromDate, toDate, pageable);
    }

    @GetMapping("/videos")
    public PaginatedResponse<AdminVideoResponse> getAdminVideos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int perPage,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "latest") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        AdminVideoSearchRequest request = AdminVideoSearchRequest.builder()
                .page(page)
                .perPage(perPage)
                .searchTerm(searchTerm)
                .categoryId(categoryId)
                .status(status)
                .sortBy(sortBy)
                .sortOrder(sortOrder)
                .build();
        return adminService.getAdminVideos(request);
    }

    @GetMapping("/earnings")
    public PaginatedResponse<AdminEarningResponse> getAdminEarnings(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int perPage,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "latest") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        AdminEarningSearchRequest request = AdminEarningSearchRequest.builder()
                .page(page)
                .perPage(perPage)
                .searchTerm(searchTerm)
                .status(status)
                .sortBy(sortBy)
                .sortOrder(sortOrder)
                .build();
        return adminService.getAdminEarnings(request);
    }

    /**
     * 특정 유저의 정보를 조회하는 메서드입니다.
     *
     * @param userId
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{user-id}")
    public UserResponse getUserInfo(@PathVariable("user-id") Long userId) {
        UserResponse userResponse = userService.getUserInfo(userId);
        return userResponse;
    }

    /**
     * 관리자가 특정 유저의 프로필 정보를 수정합니다.
     *
     * @param userId
     * @param userUpdateRequest
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{user-id}/profile")
    public UserResponse updateUserProfile(@PathVariable("user-id") Long userId,
            @RequestBody @Validated UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userId, userUpdateRequest);
    }

    /**
     * 관리자가 특정 유저의 상태를 변경합니다.(활성화/비활성화)
     *
     * @param userId
     * @param isActive
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{user-id}")
    public UserResponse updateUserActive(@PathVariable("user-id") Long userId,
            @RequestParam Boolean isActive) {
        UserResponse userResponse = userService.updateUserActive(userId, isActive);
        return userResponse;
    }

    /**
     * 관리자가 특정 유저를 삭제합니다.
     *
     * @param userId
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{user-id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user-id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // 트레이너 목록 조회 (페이징)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trainers")
    public PageImpl<TrainerResponse> getTrainers(@RequestParam Pageable pageable) {

        return trainerService.getTrainers(pageable);
    }

    // 이메일로 트레이너 찾기 (관리자용)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trainers/by-email")
    public TrainerResponse getTrainerByEmail(@RequestParam String email) {
        return trainerService.getTrainerByEmail(email);
    }

    // 정산
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/settle")
    public void settleEarningsByAdmin(@RequestParam Long trainerId) {
        earningService.settleEarningsByAdmin(trainerId);
    }

}
