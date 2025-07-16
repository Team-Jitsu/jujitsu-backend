package com.fightingkorea.platform.domain.video.repository;

import com.fightingkorea.platform.domain.video.dto.UserVideoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomUserVideoRepository {

    Page<UserVideoResponse> getPurchasedVideoList(Long userId, Pageable pageable);

}
