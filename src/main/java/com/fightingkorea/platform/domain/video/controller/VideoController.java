package com.fightingkorea.platform.domain.video.controller;

import com.fightingkorea.platform.domain.video.dto.VideoUploadRequest;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoUpdateRequest;
import com.fightingkorea.platform.domain.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public VideoResponse uploadVideo(@RequestBody @Validated VideoUploadRequest req){
        return videoService.registerVideo(req);
    }

    @PutMapping("/{video-id}")
    public VideoResponse updateVideo(
            @PathVariable ("video-id") Long videoId,
            @RequestBody VideoUpdateRequest req) {
        return videoService.updateVideo(videoId, req);
    }

    // 특정 강의를 삭제
    @DeleteMapping("/{video-id}")
    public void deleteVideo(@PathVariable ("video-id") Long videoId){
        videoService.deleteVideo(videoId);
    }


}
