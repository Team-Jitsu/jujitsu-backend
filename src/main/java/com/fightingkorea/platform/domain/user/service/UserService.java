package com.fightingkorea.platform.domain.user.service;

import com.fightingkorea.platform.domain.user.dto.RegisterRequest;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.dto.UserUpdateRequest;
import com.fightingkorea.platform.domain.user.entity.type.Role;

public interface UserService {
    UserResponse registerUser(RegisterRequest registerRequest, Role role);

    UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest);

    Void deleteUser(Long userId);
}
