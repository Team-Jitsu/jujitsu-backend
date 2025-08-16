package com.fightingkorea.platform.domain.user.controller;

import com.fightingkorea.platform.domain.user.dto.PasswordUpdateRequest;
import com.fightingkorea.platform.domain.user.dto.UserRegisterRequest;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.dto.UserUpdateRequest;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import com.fightingkorea.platform.domain.user.service.UserService;
import com.fightingkorea.platform.domain.video.dto.UserVideoResponse;
import com.fightingkorea.platform.domain.video.service.VideoService;
import com.fightingkorea.platform.global.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final VideoService videoService;

    /**
     * 수련생을 등록하는 메서드.
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerTrainee(@RequestBody @Validated UserRegisterRequest userRegisterRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.registerUser(userRegisterRequest, Role.TRAINEE));
    }

    @GetMapping("/me")
    public UserResponse getUser() {
        return userService.getUserInfo(UserUtil.getUserId());
    }

    /**
     * 유저 정보를 업데이트 하는 메서드.
     *
     * @param userUpdateRequest
     * @return
     */
    @PutMapping("/me")
    public UserResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(UserUtil.getUserId(), userUpdateRequest);
    }

    /**
     * 비밀번호를 업데이트하는 메서드입니다.
     *
     * @param passwordUpdateRequest
     * @return
     */
    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        Long userId = UserUtil.getUserId(); // JWT에서 꺼낸 유저 ID

        userService.updatePassword(userId, passwordUpdateRequest);

        return ResponseEntity.ok().build();
    }

    /**
     * 유저를 삭제하는 메서드.
     *
     * @return
     */
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser(UserUtil.getUserId()); //JWT 에서 꺼낸 userId 사용

        return ResponseEntity.noContent().build();
    }

    /**
     * 유저 목록을 조회하는 메서드.
     *
     * @param name
     * @param sex
     * @param role
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

    // 강의 구매
    @PostMapping("/{user-video}")
    public UserVideoResponse purchaseVideo(@PathVariable("user-video") Long videoId) {
        return userService.purchaseVideo(videoId);
    }

    // 강의 구매 내역을 조회하는 메서드
    @GetMapping("/videos")
    public Page<UserVideoResponse> getPurchasedVideoList(
            @PageableDefault(size = 10, sort = "purchasedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return videoService.getPurchasedVideoList(UserUtil.getUserId(), pageable);
    }

    // 현재 로그인한 사용자의 강의 구매 목록 조회 (사양 경로)
    @GetMapping("/me/purchases")
    public Page<UserVideoResponse> getMyPurchasedVideos(
            @PageableDefault(size = 10, sort = "purchasedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return videoService.getPurchasedVideoList(UserUtil.getUserId(), pageable);
    }


    // 강의 구매 내역을 삭제하는 메서드
    @DeleteMapping("/{user-video}")
    public void deletePurchasedContent(@PathVariable("user-video") Long userVideoId) {
        userService.deletePurchasedContent(userVideoId);
    }
}
