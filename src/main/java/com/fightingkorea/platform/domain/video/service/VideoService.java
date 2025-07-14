package com.fightingkorea.platform.domain.video.service;

import com.fightingkorea.platform.domain.video.dto.UserVideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoUploadRequest;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoUpdateRequest;
import com.fightingkorea.platform.domain.video.entity.UserVideo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VideoService {

    VideoResponse registerVideo(VideoUploadRequest req);

    VideoResponse updateVideo(Long videoId, VideoUpdateRequest req);

    void deleteVideo(Long videoId);

    Page<UserVideoResponse> getPurchasedVideoList(Long userId, Pageable pageable);
}
