package com.fightingkorea.platform.domain.trainer.service;

import com.fightingkorea.platform.domain.trainer.dto.TrainerRegisterRequest;
import com.fightingkorea.platform.domain.trainer.dto.TrainerRegisterResponse;
import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import com.fightingkorea.platform.domain.trainer.dto.TrainerUpdateRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface TrainerService {
    TrainerRegisterResponse createTrainer(TrainerRegisterRequest request);

    TrainerResponse getTrainer(Long trainerId);

    PageImpl<TrainerResponse> getTrainers(Pageable pageable);

    void updateTrainer(TrainerUpdateRequest trainerUpdateRequest);
}
