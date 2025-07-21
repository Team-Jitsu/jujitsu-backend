package com.fightingkorea.platform.domain.auth.service;

import com.fightingkorea.platform.domain.auth.dto.JwtResponse;
import com.fightingkorea.platform.domain.auth.dto.LoginRequest;
import com.fightingkorea.platform.domain.auth.entity.RefreshToken;
import com.fightingkorea.platform.domain.auth.repository.RefreshTokenRepository;
import com.fightingkorea.platform.domain.trainer.repository.TrainerRepository;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.exception.UserNotFoundException;
import com.fightingkorea.platform.domain.user.repository.UserRepository;
import com.fightingkorea.platform.global.auth.jwt.JwtTokenUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final JwtTokenUtil jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailAndIsActiveIsTrue(loginRequest.getUserId())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(loginRequest.getUserPassword(), user.getPassword())) {
            throw new RuntimeException();
        }

        String accessToken;
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        if (Role.TRAINER == user.getRole()) {
            Long trainerId = trainerRepository.findTrainerIdByUserId(user.getUserId())
                    .orElseThrow(UserNotFoundException::new);

            accessToken = jwtTokenProvider.createAccessToken(user.getUserId(), trainerId);
        } else {
            accessToken = jwtTokenProvider.createAccessToken(user.getUserId());
        }

        // 기존 refresh token 조회
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser_UserId(user.getUserId());

        if (existingToken.isPresent()) {
            existingToken.get().updateToken(refreshToken); // setter 또는 별도 메서드 사용
        } else {
            refreshTokenRepository.save(RefreshToken.createRefreshToken(refreshToken, user));
        }

        return new JwtResponse(accessToken, refreshToken);
    }


    public void logOut(Long userId) {
        if (Boolean.FALSE.equals(userRepository.existsByUserId(userId))) {
            throw new UserNotFoundException();
        }

        refreshTokenRepository.deleteByUser_UserId(userId);
    }
}
