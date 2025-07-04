package com.fightingkorea.platform.domain.user.controller;

import com.fightingkorea.platform.domain.user.dto.PasswordUpdateRequest;
import com.fightingkorea.platform.domain.user.dto.RegisterRequest;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.dto.UserUpdateRequest;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import com.fightingkorea.platform.domain.user.service.UserService;
import com.fightingkorea.platform.global.UserThreadLocal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 수련생을 등록하는 메서드.
     *
     * @param registerRequest
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerTrainee(@RequestBody @Validated UserRegisterRequest userRegisterRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.registerUser(userRegisterRequest, Role.TRAINEE));
    }

    /**
     * 유저 정보를 업데이트 하는 메서드.
     *
     * @param userUpdateRequest
     * @return
     */
    @PutMapping("/me")
    public UserResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest){
        UserResponse userResponse = userService.updateUser(UserThreadLocal.getUserId(), userUpdateRequest);
        // JWT에서 꺼낸 현재 로그인 유저 ID

        return userResponse;

//        return ResponseEntity
//                .ok(userResponse);

    }

    /**
     *  비밀번호를 업데이트하는 메서드입니다.
     *
     * @param passwordUpdateRequest
     * @return
     */
    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        Long userId = UserThreadLocal.getUserId(); // JWT에서 꺼낸 유저 ID

        userService.updatePassword(userId, passwordUpdateRequest);

        return ResponseEntity.ok().build();
    }

    /**
     * 유저를 삭제하는 메서드.
     *
     * @return
     */
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser(UserThreadLocal.getUserId()); //JWT 에서 꺼낸 userId 사용

        return ResponseEntity.noContent().build();
    }

    /**
     * 유저 목록을 조회하는 메서드.
     *
     * @param name
     * @param sex
     * @param role
     * @param fromDate
     * @param toDate
     * @param pageable
     * @return
     */
    @GetMapping
    public Page<User> getUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Sex sex,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate,
            Pageable pageable) {
        return userService.getUsers(name, sex, role, fromDate, toDate, pageable);
    }
}
