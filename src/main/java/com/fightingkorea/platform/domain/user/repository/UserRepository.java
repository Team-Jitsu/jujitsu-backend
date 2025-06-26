package com.fightingkorea.platform.domain.user.repository;

import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.repository.Impl.UserRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , UserRepositoryImpl {
    Optional<User> findByEmailAndIsActiveIsTrue(String email);

    Boolean existsByEmailAndIsActiveIsTrue(String email);

    Optional<User> findByUserId(Long userId);

    Page<User> searchUsers(String email, Pageable pageable);
}
