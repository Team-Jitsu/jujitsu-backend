package com.fightingkorea.platform.domain.video.controller;

import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoUpdateRequest;
import com.fightingkorea.platform.domain.video.dto.VideoUploadMultipartRequest;
import com.fightingkorea.platform.domain.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;


    @PostMapping
    public VideoResponse uploadVideo(@RequestPart @Validated VideoUploadMultipartRequest req, @RequestPart MultipartFile file){
        return videoService.uploadVideoMultipart(req, file);
    }

    // 비디오 수정
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

    @GetMapping("/{videoId}")
    public VideoResponse getVideo(@PathVariable("videoId") Long videoId) {
        return videoService.getVideo(videoId);
    }

    @GetMapping("/{videoId}/play")
    public VideoResponse getPlayUrl(@PathVariable("videoId") Long videoId) {
        return videoService.getPlayUrl(videoId);
    }


}
