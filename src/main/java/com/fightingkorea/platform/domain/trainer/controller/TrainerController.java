package com.fightingkorea.platform.domain.trainer.controller;

import com.fightingkorea.platform.domain.earning.service.EarningService;
import com.fightingkorea.platform.domain.trainer.dto.*;
import com.fightingkorea.platform.domain.trainer.service.TrainerService;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoSearchRequest;
import com.fightingkorea.platform.domain.video.service.VideoService;
import com.fightingkorea.platform.global.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;
    private final EarningService earningService;
    private final VideoService videoService;

    // 트레이너 등록
    @PostMapping("/register")
    public TrainerRegisterResponse registerTrainer(@RequestBody TrainerRegisterRequest request) {
        return trainerService.createTrainer(request);
    }

    // 트레이너 단건 조회
    @GetMapping("/me")
    public TrainerResponse getTrainer() {
        return trainerService.getTrainer(UserUtil.getTrainerId());
    }

    // 트레이너 목록 조회
    @GetMapping
    public PageImpl<TrainerResponse> getTrainers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer perPage,
            @RequestParam(required = false) Long specialtyId,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "joinDate") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder
    ) {
        int p = (page == null || page < 1) ? 0 : page - 1;
        int size = (perPage == null) ? 20 : Math.min(perPage, 100);

        Pageable pageable = PageRequest.of(p, size);

        TrainerSearchRequest request = TrainerSearchRequest.builder()
                .specialtyId(specialtyId)
                .region(region)
                .search(search)
                .sortBy(sortBy)
                .sortOrder(sortOrder)
                .build();

        return trainerService.getTrainers(request, pageable);
    }

    // 특정 트레이너의 강의 목록 조회
    @GetMapping("/{trainerId}/videos")
    public Page<VideoResponse> getTrainerVideos(
            @PathVariable Long trainerId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer perPage,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search
    ) {
        int p = (page == null || page < 1) ? 0 : page - 1;
        int size = (perPage == null) ? 20 : Math.min(perPage, 100);

        Sort sort = Sort.by("uploadTime").descending();
        Pageable pageable = PageRequest.of(p, size, sort);

        VideoSearchRequest request = VideoSearchRequest.builder()
                .trainerId(trainerId)
                .categoryId(categoryId)
                .search(search)
                .build();

        return videoService.getVideos(request, pageable);
    }

    // 트레이너 정보 수정
    @PutMapping("/me")
    public void updateTrainer(
            @RequestBody TrainerUpdateRequest updateRequest
    ) {
        trainerService.updateTrainer(updateRequest);
    }

    // 트레이너가 정산 요청
    @PostMapping("/settle")
    public void settleEarningsByTrainer() {
        earningService.settleEarningsByTrainer(UserUtil.getTrainerId());
    }
}
