package com.fightingkorea.platform.domain.video.repository;

import com.fightingkorea.platform.domain.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long>, JpaSpecificationExecutor<Video> {

    Boolean existsByTitle(String title);

    Optional<Video> findVideoByVideoId(Long videoId);

    long countByVideoCategories_CategoryId(Long categoryId);

    List<Video> findTop3ByVideoCategories_CategoryIdOrderByLikesCountDesc(Long categoryId);
}
