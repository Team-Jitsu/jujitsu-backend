package com.fightingkorea.platform.domain.user.config;

import com.fightingkorea.platform.user.domain.User;
import com.fightingkorea.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

/**
 *  Request Header 에서 X-USER-ROLE을 꺼내 권한이 있는지 확인하는 Aspect 입니다.
 *  Controller 에서 사용합니다.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class HasRoleAspect {
    private final UserRepository userRepository;

    @Before("@annotation(hasRole)")
    public void checkRole(HasRole hasRole) {
    Long userId = UserThread.getUserId();
    User user = userRepository.findByUserId(userId).orElse(null);

        if (user.getRole() == null || Arrays.stream(hasRole.value()).noneMatch(user.getRole()::equals)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }
}
