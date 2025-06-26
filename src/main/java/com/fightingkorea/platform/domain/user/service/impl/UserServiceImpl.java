package com.fightingkorea.platform.domain.user.service.impl;

import com.fightingkorea.platform.domain.user.dto.RegisterRequest;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.exception.UserConflictException;
import com.fightingkorea.platform.domain.user.repository.UserRepository;
import com.fightingkorea.platform.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        return new UserResponse(
                user.getUserId(),
                user.getNickname(),
                user.getRole().getLabel(),
                user.getCreatedAt()
        );
    }
}
