package com.fightingkorea.platform.domain.video.controller;

import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoSearchRequest;
import com.fightingkorea.platform.domain.video.dto.VideoUpdateRequest;
import com.fightingkorea.platform.domain.video.dto.VideoUploadMultipartRequest;
import com.fightingkorea.platform.domain.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // 비디오 목록 조회
    @GetMapping
    public Page<VideoResponse> getVideos(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer perPage,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long trainerId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false, defaultValue = "latest") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder
    ) {
        int p = (page == null || page < 1) ? 0 : page - 1;
        int size = (perPage == null) ? 20 : Math.min(perPage, 100);

        String sortField;
        switch (sortBy) {
            case "price":
                sortField = "price";
                break;
            case "popularity":
                sortField = "likesCount";
                break;
            default:
                sortField = "uploadTime";
        }

        Sort sort = "asc".equalsIgnoreCase(sortOrder)
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(p, size, sort);

        VideoSearchRequest request = VideoSearchRequest.builder()
                .categoryId(categoryId)
                .trainerId(trainerId)
                .search(search)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();

        return videoService.getVideos(request, pageable);
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
