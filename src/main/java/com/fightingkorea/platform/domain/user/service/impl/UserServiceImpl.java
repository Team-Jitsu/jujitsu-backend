package com.fightingkorea.platform.domain.user.service.impl;

import com.fightingkorea.platform.domain.auth.repository.RefreshTokenRepository;
import com.fightingkorea.platform.domain.user.dto.PasswordUpdateRequest;
import com.fightingkorea.platform.domain.user.dto.RegisterRequest;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.dto.UserUpdateRequest;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import com.fightingkorea.platform.domain.user.exception.*;
import com.fightingkorea.platform.domain.user.repository.UserRepository;
import com.fightingkorea.platform.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Override
    public UserResponse registerUser(UserRegisterRequest userRegisterRequest, Role role) {
        log.info("회원가입 시도: email={}, role={}", userRegisterRequest.getEmail(), role.name());

        if (userRepository.existsByEmailAndIsActiveIsTrue(userRegisterRequest.getEmail())) {
            log.warn("회원가입 실패 - 중복 이메일: {}", userRegisterRequest.getEmail());
            throw new UserConflictException();
        }

        User user = User.createUser(userRegisterRequest, role);
        userRepository.save(user);

        log.info("회원가입 성공: userId={}, email={}", user.getUserId(), user.getEmail());

        return ResponseMapper.toUserResponse(user);
    }


    /**
     * 유저 정보를 수정합니다.
     *
     * @param userId
     * @param userUpdateRequest
     * @return
     */
    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        User foundUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException());
        foundUser.updateUser(
                userUpdateRequest.getAge(), userUpdateRequest.getSex(),
                userUpdateRequest.getNickname(), userUpdateRequest.getRegion(),
                userUpdateRequest.getMobileNumber(), userUpdateRequest.getGymLocation());

        userRepository.save(foundUser);

        return ResponseMapper.toResponse(foundUser);
    }

    @Override
    public void deleteUser(Long userId) {

        User deleteTargetUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException());

        deleteTargetUser.updateActive(true);

        refreshTokenRepository.deleteByUser_UserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUsers(String nickname, Sex sex, Role role,
                               LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable){

        // 1. 날짜 범위 유효성 체크
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            throw new InvalidDateRangeException();
        }

        // 2. 닉네임 길이 제한
        if (nickname != null && (nickname.length() > 10)) {
            throw new InvalidNicknameLengthException();
        }

        // 3. 닉네임에 금지어 포함 여부 (예시 : 욕설, 시스템 예약어 등)
        if (nickname != null && containsForbiddenWords(nickname)) {
            throw new ForbiddenWordInNicknameException();
        }

        // 4. 성별 값 유효성 검사 (null 이거나 enum 중 하나인지 )
        if (sex != null && !EnumSet.allOf(Sex.class).contains(sex)) {
            throw new InvalidSexValueException();
        }

        // 5. 조회
        Page<User> users = userRepository.searchUsers(nickname, sex, fromDate, toDate, pageable);

        // 6. 검색 결과 없으면 예외
        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }

        return users;
    }

    /**
     * 로그인된 사용자가 자신의 비밀번호를 업데이트 합니다.
     * 기존 비밀번호 확인 -> 새 비밀번호로 업데이트.
     *
     * @param userId
     * @param passwordUpdateRequest
     */
    @Override
    public void updatePassword(Long userId, PasswordUpdateRequest passwordUpdateRequest) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException());

        // 현재 비밀번호 검증 (유저가 진짜 본인인지 확인)
        if (!passwordEncoder.matches(passwordUpdateRequest.getCurrentPassword(), user.getPassword())){
            throw new InvalidCurrentPasswordException();
        }

        // 새 비밀번호 암호화 후 저장
        user.updatePassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserInfo(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException());

        return ResponseMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUserActive(Long userId, Boolean isActive) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException());

        user.updateActive(isActive);
        return ResponseMapper.toResponse(user);
    }

    private boolean containsForbiddenWords(String nickname) {
        List<String> forbiddenWords = List.of("욕설1", "욕설2");
        return forbiddenWords.stream().anyMatch(nickname.toLowerCase()::contains);
    }

}
