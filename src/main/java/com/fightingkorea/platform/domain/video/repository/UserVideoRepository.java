package com.fightingkorea.platform.domain.video.repository;

import com.fightingkorea.platform.domain.video.entity.UserVideo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVideoRepository extends JpaRepository<UserVideo, Long>, CustomUserVideoRepository {

}
