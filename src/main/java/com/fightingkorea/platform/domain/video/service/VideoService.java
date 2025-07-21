package com.fightingkorea.platform.domain.video.service;

import com.fightingkorea.platform.domain.video.dto.UserVideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoUploadRequest;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoService {

    VideoResponse uploadVideo(VideoUploadRequest req);

    VideoResponse updateVideo(Long videoId, VideoUpdateRequest req);

    void deleteVideo(Long videoId);

    // 페이징된 특정 유저의 강의 구매 리스트 조회
    Page<UserVideoResponse> getPurchasedVideoList(Long userId, Pageable pageable);
}
