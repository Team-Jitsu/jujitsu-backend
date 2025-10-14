package com.fightingkorea.platform.domain.trainer.service;

import com.fightingkorea.platform.domain.trainer.dto.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface TrainerService {
    TrainerRegisterResponse createTrainer(TrainerRegisterRequest request);

    TrainerResponse getTrainer(Long trainerId);

    TrainerResponse getTrainerByEmail(String email);

    PageImpl<TrainerResponse> getTrainers(Pageable pageable);

    PageImpl<TrainerResponse> getTrainers(TrainerSearchRequest request, Pageable pageable);

    void updateTrainer(TrainerUpdateRequest trainerUpdateRequest);
}
