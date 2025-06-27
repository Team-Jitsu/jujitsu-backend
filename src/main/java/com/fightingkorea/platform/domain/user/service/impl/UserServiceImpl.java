package com.fightingkorea.platform.domain.user.service.impl;

import com.fightingkorea.platform.domain.user.dto.RegisterRequest;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.dto.UserUpdateRequest;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import com.fightingkorea.platform.domain.user.exception.InvalidDateRangeException;
import com.fightingkorea.platform.domain.user.exception.UserConflictException;
import com.fightingkorea.platform.domain.user.exception.UserNotFoundException;
import com.fightingkorea.platform.domain.user.repository.UserRepository;
import com.fightingkorea.platform.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse registerUser(RegisterRequest registerRequest, Role role) {
        log.info("회원가입 시도: email={}, role={}", registerRequest.getEmail(), role.name());

        if (userRepository.existsByEmailAndIsActiveIsTrue(registerRequest.getEmail())) {
            log.warn("회원가입 실패 - 중복 이메일: {}", registerRequest.getEmail());
            throw new UserConflictException();
        }

        User user = User.createUser(registerRequest, role);
        userRepository.save(user);

        log.info("회원가입 성공: userId={}, email={}", user.getUserId(), user.getEmail());

        return ResponseMapper.toResponse(user);
    }


    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }
        User foundUser = optionalUser.get();
        foundUser.updateUser(
                userUpdateRequest.getAge(),userUpdateRequest.getSex(),
                userUpdateRequest.getNickname(), userUpdateRequest.getRegion(),
                userUpdateRequest.getMobileNumber(), userUpdateRequest.getGymLocation());
        userRepository.save(foundUser);
        return ResponseMapper.toResponse(foundUser);
    }

    @Override
    public Void deleteUser(Long userId) {

        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }
        User deleteTargetUser = optionalUser.get();
        userRepository.delete(deleteTargetUser);
        return null;

    }

    @Override
    public Page<User> getUsers(String nickname, Sex sex,
                               LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable){

        // 1. 날짜 범위 유효성 체크
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            throw new InvalidDateRangeException();
        }

        // 2. 조회
        Page<User> users = userRepository.searchUsers(nickname, sex, fromDate, toDate, pageable);

        // 3. 검색 결과 없으면 예외
        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }

        return users;
    }
}
