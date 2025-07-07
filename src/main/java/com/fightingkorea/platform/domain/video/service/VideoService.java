package com.fightingkorea.platform.domain.video.service;

import com.fightingkorea.platform.domain.video.dto.VideoRegisterRequest;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoUpdateRequest;
import com.fightingkorea.platform.domain.video.entity.Video;
import org.springframework.data.domain.Page;

public interface VideoService {

    VideoResponse registerVideo(VideoRegisterRequest req);

    VideoResponse updateVideo(Long videoId, Long trainerId, VideoUpdateRequest req);

    void deleteVideo(Long videoId);

    Page<Video> getVideos(String title, Integer price, )
}
