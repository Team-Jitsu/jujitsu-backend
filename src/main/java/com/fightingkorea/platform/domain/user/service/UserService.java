package com.fightingkorea.platform.domain.user.service;

import com.fightingkorea.platform.domain.user.dto.PasswordUpdateRequest;
import com.fightingkorea.platform.domain.user.dto.UserRegisterRequest;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.dto.UserUpdateRequest;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import com.fightingkorea.platform.domain.video.dto.UserVideoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface UserService {

    /**
     * 유저를 등록합니다.
     *
     * @param registerRequest
     * @param role
     * @return
     */
    UserResponse registerUser(UserRegisterRequest registerRequest, Role role);

    /**
     * 유저 정보를 수정합니다.
     *
     * @param userId
     * @param userUpdateRequest
     * @return
     */
    UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest);

    // admin만 필요한거: 유저 권한 수정

    /**
     * 유저를 삭제합니다.
     *
     * @param userId
     * @return
     */
    void deleteUser(Long userId);

    /**
     * 조건에 맞는 유저 목록을 가져옵니다.
     *
     * @param name
     * @param sex
     * @param role
     * @param fromDate
     * @param toDate
     * @param pageable
     * @return
     */
    Page<UserResponse> getUsers(String name, Sex sex, Role role,
                        LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    /**
     * 비밀번호를 업데이트 합니다.
     *
     * @param userId
     * @param passwordUpdateRequest
     */
    void updatePassword(Long userId, PasswordUpdateRequest passwordUpdateRequest);

    /**
     * 유저 아이디로 유저의 정보를 확인합니다.
     *
     * @param userId
     */
    UserResponse getUserInfo(Long userId);


    /**
     * 유저의 상태를 업데이트 합니다.
     *
     * @param userId
     */
    UserResponse updateUserActive(Long userId, Boolean isActive);

    // 구매 내역 삭제(환불)
    void deletePurchasedContent(Long userVideoId);

    // 강의 구매
    UserVideoResponse purchaseVideo(Long videoId);
}
