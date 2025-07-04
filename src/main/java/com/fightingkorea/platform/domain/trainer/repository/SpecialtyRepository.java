package com.fightingkorea.platform.domain.trainer.repository;

import com.fightingkorea.platform.domain.trainer.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    Boolean existsBySpecialtyName(String specialtyName);
}
