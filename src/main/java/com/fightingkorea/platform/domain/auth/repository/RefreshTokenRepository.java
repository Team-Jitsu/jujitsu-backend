package com.fightingkorea.platform.domain.auth.repository;

import com.fightingkorea.platform.domain.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByUser_UserId(Long userId);
}