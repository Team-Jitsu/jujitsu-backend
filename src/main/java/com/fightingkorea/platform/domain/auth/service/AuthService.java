package com.fightingkorea.platform.domain.auth.service;

import com.fightingkorea.platform.domain.auth.dto.JwtResponse;
import com.fightingkorea.platform.domain.auth.dto.LoginRequest;
import com.fightingkorea.platform.domain.auth.entity.RefreshToken;
import com.fightingkorea.platform.domain.auth.repository.RefreshTokenRepository;
import com.fightingkorea.platform.domain.user.entity.User;

import com.fightingkorea.platform.domain.user.repository.UserRepository;
import com.fightingkorea.platform.global.auth.jwt.JwtTokenUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailAndIsActiveIsTrue(loginRequest.getUserId())
                .orElseThrow(() -> new RuntimeException());

        if (!passwordEncoder.matches(loginRequest.getUserPassword(), user.getPassword())) {
            throw new RuntimeException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(loginRequest.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(loginRequest.getUserId());

        RefreshToken userRefreshToken = new RefreshToken(refreshToken, user);

        refreshTokenRepository.save(userRefreshToken);

        return new JwtResponse(accessToken, refreshToken);
    }

}
