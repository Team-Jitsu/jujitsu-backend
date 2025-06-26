package com.fightingkorea.platform.domain.user.service.impl;

import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.entity.User;

public class ResponseMapper  {
    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getUserId(), user.getNickname(),
                user.getRole().getLabel(), user.getCreatedAt());
    }
}
