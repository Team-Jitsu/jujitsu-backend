package com.fightingkorea.platform.domain.trainer.repository;

import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long>, CustomTrainerRepository {
}
