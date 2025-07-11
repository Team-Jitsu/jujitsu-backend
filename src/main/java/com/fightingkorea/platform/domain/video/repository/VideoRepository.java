package com.fightingkorea.platform.domain.video.repository;

import com.fightingkorea.platform.domain.video.entity.Video;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Boolean existsByTitle(String title);

    Boolean existsVideoByVideoId(Long videoId);

    Optional<Video> findVideoByVideoId(Long videoId);
}
