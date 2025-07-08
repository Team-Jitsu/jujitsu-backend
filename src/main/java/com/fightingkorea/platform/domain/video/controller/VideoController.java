package com.fightingkorea.platform.domain.video.controller;

import com.fightingkorea.platform.domain.video.dto.VideoRegisterRequest;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoUpdateRequest;
import com.fightingkorea.platform.domain.video.entity.UserVideo;
import com.fightingkorea.platform.domain.video.entity.Video;
import com.fightingkorea.platform.domain.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;


    @PostMapping("/register")
    public VideoResponse registerVideo(@RequestBody @Validated VideoRegisterRequest req){
        return videoService.registerVideo(req);
    }

    @PutMapping("/{video-id}")
    public VideoResponse updateVideo(
            @PathVariable ("video-id") Long videoId,
            @RequestParam Long trainerId,
            @RequestBody VideoUpdateRequest req){
        return videoService.updateVideo(videoId, trainerId, req);
    }

    @DeleteMapping("/{video-id}")
    public void deleteVideo(@PathVariable ("video-id") Long videoId){
        videoService.deleteVideo(videoId);
    }

//    @GetMapping
//    public Page<Video> getVideos(
//            @RequestParam(required = false) String
//            ){}

    // 강의 판매 내역을 조회하는 메서드
    @GetMapping
    public Page<UserVideo> getVideoSoldList(){}

}
