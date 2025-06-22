package com.fightingkorea.platform.user.repository;

import com.fightingkorea.platform.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
