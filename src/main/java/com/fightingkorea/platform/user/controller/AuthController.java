package com.fightingkorea.platform.user.controller;

import com.fightingkorea.platform.UserThread;
import com.fightingkorea.platform.user.dto.LoginRequest;
import com.fightingkorea.platform.user.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * 로그인, 토큰 재발급, 로그아웃
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
    }

    @GetMapping("/admin/users/me")
    @HasRole(Role.TRAINEE.getName()) 만들기

    유저 목록 페이징(QueryDSL), 유저 정보 수정, 회원 탈퇴

    public UserResponse getMe() {
        return userRepository.findById(UserThread.getUserId());
    }
}
