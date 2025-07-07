package com.fightingkorea.platform.domain.trainer.service.impl;

import com.fightingkorea.platform.domain.trainer.dto.SpecialtyResponse;
import com.fightingkorea.platform.domain.trainer.entity.Specialty;
import com.fightingkorea.platform.domain.trainer.entity.TrainerSpecialty;
import com.fightingkorea.platform.domain.trainer.exception.SpecialtyConflictException;
import com.fightingkorea.platform.domain.trainer.exception.SpecialtyNotFoundException;
import com.fightingkorea.platform.domain.trainer.repository.SpecialtyRepository;
import com.fightingkorea.platform.domain.trainer.repository.TrainerSpecialtyRepository;
import com.fightingkorea.platform.domain.trainer.service.SpecialtyService;
import com.fightingkorea.platform.domain.user.service.impl.ResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;
    private final TrainerSpecialtyRepository trainerSpecialtyRepository;

    // 전체 전문 분야 목록 조회
    @Transactional(readOnly = true)
    @Override
    public List<SpecialtyResponse> getAllSpecialty() {
        List<Specialty> specialties = specialtyRepository.findAll(Sort.by(Sort.Direction.ASC, "specialtyName"));

        log.info("전체 전문 분야 목록 조회 완료, 총 개수: {}", specialties.size());

        return specialties.stream()
                .map(ResponseMapper::toSpecialtyResponse)
                .toList();
    }

    // 트레이너 전문 분야 설정 (기존 데이터 삭제 후 재등록)
    @Override
    public void setTrainerSpecialties(Long trainerId, List<Long> specialtyIds) {
        trainerSpecialtyRepository.deleteByTrainerTrainerId(trainerId);
        log.info("기존 전문 분야 삭제 완료, 트레이너 ID: {}", trainerId);

        List<TrainerSpecialty> trainerSpecialties = specialtyIds.stream()
                .map(specialtyId -> TrainerSpecialty.createTrainerSpecialty(
                        specialtyId,
                        trainerId
                ))
                .toList();

        trainerSpecialtyRepository.saveAll(trainerSpecialties);

        log.info("트레이너 전문 분야 설정 완료, 트레이너 ID: {}, 등록 개수: {}", trainerId, trainerSpecialties.size());
    }

    // 전문 분야 생성
    @Override
    public SpecialtyResponse createSpecialty(String specialtyName) {
        if (Boolean.TRUE.equals(specialtyRepository.existsBySpecialtyName(specialtyName))) {
            throw new SpecialtyConflictException();
        }

        Specialty specialty = specialtyRepository.save(new Specialty(specialtyName));

        log.info("전문 분야 등록 완료, ID: {}, 이름: {}", specialty.getSpecialtyId(), specialtyName);

        return ResponseMapper.toSpecialtyResponse(specialty);
    }

    // 전문 분야 삭제
    @Override
    public void deleteSpecialty(Long specialtyId) {
        if (!specialtyRepository.existsById(specialtyId)) {
            throw new SpecialtyNotFoundException();
        }

        specialtyRepository.deleteById(specialtyId);
        log.info("전문 분야 삭제 완료, ID: {}", specialtyId);
    }

}
