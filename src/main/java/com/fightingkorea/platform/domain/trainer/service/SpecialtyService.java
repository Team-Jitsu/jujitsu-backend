package com.fightingkorea.platform.domain.trainer.service;

import com.fightingkorea.platform.domain.trainer.dto.SpecialtyResponse;

import java.util.List;

public interface SpecialtyService {
    List<SpecialtyResponse> getAllSpecialty();

    void deleteSpecialty(Long specialtyId);

    void setTrainerSpecialties(Long trainerId, List<Long> specialtyIds);

    SpecialtyResponse createSpecialty(String specialtyName);
}
