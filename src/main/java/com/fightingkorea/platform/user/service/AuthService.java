package com.fightingkorea.platform.user.service;

import com.fightingkorea.platform.common.jwt.JwtTokenUtil;
import com.fightingkorea.platform.user.domain.RefreshToken;
import com.fightingkorea.platform.user.domain.User;
import com.fightingkorea.platform.user.dto.JwtResponse;
import com.fightingkorea.platform.user.dto.LoginRequest;
import com.fightingkorea.platform.user.repository.RefreshTokenRepository;
import com.fightingkorea.platform.user.repository.UserRepository;
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
        User user = userRepository.findByEmail(loginRequest.getUserId())
                .orElseThrow(() -> new RuntimeException());

        if(!passwordEncoder.matches(loginRequest.getUserPassword(), user.getPassword())) {
            throw new RuntimeException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(loginRequest.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(loginRequest.getUserId());

        RefreshToken userRefreshToken = new RefreshToken(refreshToken, user);

        refreshTokenRepository.save(userRefreshToken);

        return new JwtResponse(accessToken, refreshToken);
    }


    만료가 되었다는걸 알아야겠지
    만료가 되면은 다시 재발급
            exception
    숙제:> logout(블랙리스트) refersh, securityConfig에서 security userDetails에 저장하는 로직
}
