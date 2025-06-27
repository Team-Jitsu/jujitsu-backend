package com.fightingkorea.platform.domain.user.controller;

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

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerTrainee(@RequestBody @Validated RegisterRequest registerRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.registerUser(registerRequest, Role.TRAINEE));
    }

    @PutMapping("/me")
    public UserResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest){
        UserResponse userResponse = userService.updateUser(UserThreadLocal.getUserId(), userUpdateRequest);

        return userResponse; //이렇게 하면
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(){
        Void userResponse = userService.deleteUser(UserThreadLocal.getUserId());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(userResponse);
    }

    @GetMapping
    public Page<User> getUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Sex sex,
            @RequestParam(required = false) LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate,
            Pageable pageable) {
        return userService.getUsers(name, sex, fromDate, toDate, pageable);
    }
}
