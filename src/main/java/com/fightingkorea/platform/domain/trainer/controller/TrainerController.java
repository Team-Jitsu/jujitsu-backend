package com.fightingkorea.platform.domain.trainer.controller;

import com.fightingkorea.platform.domain.earning.service.EarningService;
import com.fightingkorea.platform.domain.trainer.dto.TrainerRegisterRequest;
import com.fightingkorea.platform.domain.trainer.dto.TrainerRegisterResponse;
import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import com.fightingkorea.platform.domain.trainer.dto.TrainerUpdateRequest;
import com.fightingkorea.platform.domain.trainer.service.TrainerService;
import com.fightingkorea.platform.global.UserThreadLocal;
import lombok.RequiredArgsConstructor;
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
        return trainerService.getTrainer(UserThreadLocal.getTrainerId());
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
    public void settleEarningsByTrainer(){
        earningService.settleEarningsByTrainer(UserThreadLocal.getTrainerId());
    }
}
