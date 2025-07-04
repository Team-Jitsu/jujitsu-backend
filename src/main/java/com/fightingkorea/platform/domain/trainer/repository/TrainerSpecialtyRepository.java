package com.fightingkorea.platform.domain.trainer.repository;

import com.fightingkorea.platform.domain.trainer.entity.TrainerSpecialty;
import com.fightingkorea.platform.domain.trainer.entity.TrainerSpecialtyId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainerSpecialtyRepository extends CrudRepository<TrainerSpecialty, TrainerSpecialtyId> {
    List<TrainerSpecialty> findByTrainerTrainerId(Long trainerTrainerId);

    void deleteByTrainerTrainerId(Long trainerId);
}
