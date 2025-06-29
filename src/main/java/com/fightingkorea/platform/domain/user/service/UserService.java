package com.fightingkorea.platform.domain.user.service;

import com.fightingkorea.platform.domain.user.dto.RegisterRequest;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.dto.UserUpdateRequest;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface UserService {
    UserResponse registerUser(RegisterRequest registerRequest, Role role);

    UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest);

    Void deleteUser(Long userId);

    Page<User> getUsers(String name, Sex sex,
                        LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
}
