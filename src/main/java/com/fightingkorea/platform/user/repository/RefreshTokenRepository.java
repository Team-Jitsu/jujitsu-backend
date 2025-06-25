package com.fightingkorea.platform.user.repository;

import com.fightingkorea.platform.user.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByUser_UserId(Long userId);
}