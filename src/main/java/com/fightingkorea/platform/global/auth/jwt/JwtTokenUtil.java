package com.fightingkorea.platform.global.auth.jwt;

import com.fightingkorea.platform.domain.auth.repository.RefreshTokenRepository;
import com.fightingkorea.platform.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT 토큰을 생성하고 검증하는 클래스
 * <p>
 * Access Token은 사용자 인증정보(로그인)용.
 * Refresh Token은 Access Token이 만료되었을 때 다시 발급받기 위한 장기 토큰.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    // Access Token 유효 시간: 30분
    private final static long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 30;
    // Refresh Token 유효 시간: 7일
    private final static long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    // 토큰을 만들 때 사용할 비밀 키 (서버에서만 알고 있는 비밀번호 같은 것)
    private final String accessSecret = "HD2J8L2gPZTLOvnqS8u0KAYGuoqD0UCkNnkUE5wGflk="; // Access 토큰 서명용 키
    private final String refreshSecret = "HD2J8L2gPZTLOvnqS8u0KAYGuoqD0UCkNnkUE5wGflk="; //Refresh 토큰 서명용 키

    //Access Token 생성 : 유저 ID와 권한 정보를 넣어서 생성
    public String createAccessToken(String userId) {
        return createToken(userId, ACCESS_TOKEN_VALIDITY, accessSecret);
    }

    // Refresh Token 생성 :
    public String createRefreshToken(String userId) {
        return createToken(userId, REFRESH_TOKEN_VALIDITY, refreshSecret);
    }

    // 실제로 토큰을 생성하는 공통 로직
    private String createToken(String userId, long validity, String secret) {
        Date now = new Date();
        // claims : 토큰 안에 담고 싶은 정보(ex. 유저ID, 권한 등)
        Claims claims = Jwts.claims().setSubject(userId); //'subject'는 대표적으로 사용자 ID를 담는 위치.

        // 만료 시간 계산 (현재 시간 + 유효 시간)
        Date expiry = new Date(now.getTime() + validity);

        // JWT 토큰을 실제로 만들어서 문자열로 반환
        return Jwts.builder()
                .setClaims(claims)      // 위에서 만든 사용자 정보 넣기
                .setIssuedAt(now)       // 토큰 발급 시간
                .setExpiration(expiry)  // 토큰 만료 시간
                .signWith(
                        Keys.hmacShaKeyFor(secret.getBytes()), // 비밀 키는 바이트 배열로 변환해야 함
                        SignatureAlgorithm.HS256                // 사용할 서명 알고리즘: HS256
                )
                .compact();             // 토큰을 문자열로 완성
    }

    // 토큰이 유효한지 확인( Access 또는 Refresh 구분해서 검사 가능)

    /**
     * JWT 토큰이 유요한지 검증하는 함수.
     *
     * @param token
     * @param isAccess 이 토큰이 Access Token인지, Refresh Token인지 구분하는 역할을 한다.
     * @return
     */
    public boolean validateToken(String token, boolean isAccess) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey((isAccess ? accessSecret : refreshSecret).getBytes())// 비밀 키 설정(Access/Refresh 구분)
                    .build()
                    .parseClaimsJws(token); // 토큰을 해석해서 문제가 없으면 유효하다고 판단함
            return true;
        } catch (JwtException e) {
            // 토큰이 유효하지 않거나 서명이 틀렸거나 만료됐을 때 여기로 들어옴
            return false;
        }
    }

    public String getUserId(String token, boolean isAccess) {
        return Jwts.parserBuilder()
                .setSigningKey((isAccess ? accessSecret : refreshSecret).getBytes())
                .build()
                .parseClaimsJws(token) //토큰을 해석
                .getBody()             // 해석된 본문(claims)을 가져옴
                .getSubject();         // 그 안에서 'subject'에 해당하는 값을 꺼냄 -> 유저 ID
    }


    public String refresh(String refreshToken) {
        // 1. 토큰 유효성 검사
        if (!validateToken(refreshToken, false)) {
            throw new RuntimeException("유효하지 않은 Refresh Token입니다.");
        }

        // 2. 사용자 ID 추출
        Long userId = Long.parseLong(getUserId(refreshToken, false));

        // 3. DB에서 해당 사용자 조회 (refresh token 포함)
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException();
        }

        String getRefreshTokenByUserId = refreshTokenRepository.findByUser_UserId(userId).get().getRefreshToken();

        // 4. 저장된 refresh token과 비교
        if (!refreshToken.equals(getRefreshTokenByUserId)) {
            throw new RuntimeException("Refresh Token이 일치하지 않습니다.");
        }

        // 5. 새 Access Token 발급
        return createAccessToken(userId.toString());
    }
}
