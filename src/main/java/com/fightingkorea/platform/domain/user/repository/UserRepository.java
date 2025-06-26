package com.fightingkorea.platform.domain.user.repository;

import com.fightingkorea.platform.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndIsActiveIsTrue(String email);

    Boolean existsByEmailAndIsActiveIsTrue(String email);
}
