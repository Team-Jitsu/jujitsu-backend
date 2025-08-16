package com.fightingkorea.platform.domain.video.service;

import com.fightingkorea.platform.domain.video.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoService {


    // 비디오 목록 조회 (페이징)
    Page<VideoResponse> getVideos(Pageable pageable);
    

    VideoResponse updateVideo(Long videoId, VideoUpdateRequest req);

    void deleteVideo(Long videoId);

    // 페이징된 특정 유저의 강의 구매 리스트 조회
    Page<UserVideoResponse> getPurchasedVideoList(Long userId, PurchaseSearchRequest request, Pageable pageable);

    VideoResponse uploadVideoMultipart(VideoUploadMultipartRequest req, org.springframework.web.multipart.MultipartFile file);

    

    

    VideoResponse getPlayUrl(Long videoId);

    VideoResponse getVideo(Long videoId);

}
