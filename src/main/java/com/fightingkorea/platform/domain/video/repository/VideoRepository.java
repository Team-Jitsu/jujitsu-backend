package com.fightingkorea.platform.domain.video.repository;

import com.fightingkorea.platform.domain.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long>, JpaSpecificationExecutor<Video> {

    Boolean existsByTitle(String title);

    Optional<Video> findVideoByVideoId(Long videoId);
}
