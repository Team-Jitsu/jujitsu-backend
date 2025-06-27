package com.fightingkorea.platform.domain.user.repository;

import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface UserRepositoryCustom {

    Page<User> searchUsers(String name, Sex sex, LocalDateTime fromDate,
                           LocalDateTime toDate, Pageable pageable);
}
