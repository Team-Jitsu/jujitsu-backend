package com.fightingkorea.platform.user.dto;

import lombok.AllArgsConstructor;

/**
 * 클라이언트에게 Access/Refresh Token을 Json으로 보내기 위한 DTO
 */
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;

    private String refreshToken;
}
