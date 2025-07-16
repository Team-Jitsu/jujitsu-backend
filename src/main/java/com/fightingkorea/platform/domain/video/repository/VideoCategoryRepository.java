package com.fightingkorea.platform.domain.video.repository;

import com.fightingkorea.platform.domain.video.entity.VideoCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoCategoryRepository extends JpaRepository<VideoCategory, Long> {

    void deleteByVideoId(Long videoId);
}
