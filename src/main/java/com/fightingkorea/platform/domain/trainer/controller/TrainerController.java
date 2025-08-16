package com.fightingkorea.platform.domain.trainer.controller;

import com.fightingkorea.platform.domain.earning.service.EarningService;
import com.fightingkorea.platform.domain.trainer.dto.TrainerRegisterRequest;
import com.fightingkorea.platform.domain.trainer.dto.TrainerRegisterResponse;
import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import com.fightingkorea.platform.domain.trainer.dto.TrainerUpdateRequest;
import com.fightingkorea.platform.domain.trainer.service.TrainerService;
import com.fightingkorea.platform.global.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;
    private final EarningService earningService;

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
    public PageImpl<TrainerResponse> getTrainers(@PageableDefault(size = 20) Pageable pageable) {
        return trainerService.getTrainers(pageable);
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
