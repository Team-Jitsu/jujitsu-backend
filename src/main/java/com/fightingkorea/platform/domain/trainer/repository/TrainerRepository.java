package com.fightingkorea.platform.domain.trainer.repository;

import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long>, CustomTrainerRepository {
    @Query("SELECT t.trainerId FROM Trainer t WHERE t.user.userId = :userId")
    Optional<Long> findTrainerIdByUserId(@Param("userId") Long userId);
}
