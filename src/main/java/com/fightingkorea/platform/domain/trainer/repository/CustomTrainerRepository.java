package com.fightingkorea.platform.domain.trainer.repository;

import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface CustomTrainerRepository {
    PageImpl<TrainerResponse> findBySomeCondition(Pageable pageable);
}
