package com.fightingkorea.platform.domain.trainer.service.impl;

import com.fightingkorea.platform.domain.trainer.dto.*;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.trainer.entity.TrainerSpecialty;
import com.fightingkorea.platform.domain.trainer.exception.TrainerNotFoundException;
import com.fightingkorea.platform.domain.trainer.repository.TrainerRepository;
import com.fightingkorea.platform.domain.trainer.repository.TrainerSpecialtyRepository;
import com.fightingkorea.platform.domain.trainer.service.SpecialtyService;
import com.fightingkorea.platform.domain.trainer.service.TrainerService;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.repository.UserRepository;
import com.fightingkorea.platform.domain.user.service.UserService;
import com.fightingkorea.platform.global.common.response.ResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final SpecialtyService specialtyService;
    private final TrainerSpecialtyRepository trainerSpecialtyRepository;

    // 트레이너 생성: 유저 등록 후 트레이너 엔티티 저장 및 전문 분야 설정
    @Override
    public TrainerRegisterResponse createTrainer(TrainerRegisterRequest request) {
        log.info("트레이너 생성 요청 수신");

        // 유저 등록
        UserResponse userResponse = userService.registerUser(request.getUserRegisterRequest(), Role.TRAINER);
        User user = userRepository.getReferenceById(userResponse.getUserId());

        // 트레이너 엔티티 생성 및 저장
        Trainer trainer = Trainer.createTrainer(
                user,
                request.getAccountOwnerName(),
                request.getBio(),
                request.getAccountNumber(),
                request.getAutomaticSettlement());
        trainerRepository.save(trainer);

        // 전문 분야 설정
        specialtyService.setTrainerSpecialties(trainer.getTrainerId(), request.getSpecialtyIds());

        log.info("트레이너 생성 완료, 트레이너 ID: {}", trainer.getTrainerId());

        return new TrainerRegisterResponse(
                trainer.getTrainerId(),
                trainer.getAutomaticSettlement(),
                request.getSpecialtyIds(),
                ResponseMapper.toUserResponse(user));
    }

    // 단일 트레이너 조회, 없으면 예외 발생
    @Transactional(readOnly = true)
    @Override
    public TrainerResponse getTrainer(Long trainerId) {
        log.info("트레이너 단건 조회 요청, 트레이너 ID: {}", trainerId);

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(TrainerNotFoundException::new);

        List<TrainerSpecialty> trainerSpecialtyList = trainerSpecialtyRepository.findByTrainerTrainerId(trainerId);

        log.info("트레이너 조회 성공, 트레이너 ID: {}", trainerId);

        return ResponseMapper.toTrainerResponse(trainer, trainerSpecialtyList);
    }

    // 이메일로 트레이너 조회
    @Transactional(readOnly = true)
    @Override
    public TrainerResponse getTrainerByEmail(String email) {
        log.info("이메일로 트레이너 조회 요청, 이메일: {}", email);

        Trainer trainer = trainerRepository.findByUserEmail(email)
                .orElseThrow(() -> new TrainerNotFoundException("해당 이메일의 트레이너를 찾을 수 없습니다: " + email));

        List<TrainerSpecialty> trainerSpecialtyList = trainerSpecialtyRepository
                .findByTrainerTrainerId(trainer.getTrainerId());

        log.info("이메일로 트레이너 조회 성공, 트레이너 ID: {}", trainer.getTrainerId());

        return ResponseMapper.toTrainerResponse(trainer, trainerSpecialtyList);
    }

    // 페이징된 트레이너 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public PageImpl<TrainerResponse> getTrainers(TrainerSearchRequest request, Pageable pageable) {
        log.info("트레이너 목록 조회 요청, 페이지 번호: {}, 페이지 크기: {}", pageable.getPageNumber(), pageable.getPageSize());

        PageImpl<TrainerResponse> result = trainerRepository.search(request, pageable);

        log.info("트레이너 목록 조회 완료, 조회 수: {}", result.getNumberOfElements());

        return result;
    }

    // 트레이너 정보 수정
    @Override
    public void updateTrainer(TrainerUpdateRequest trainerUpdateRequest) {
        log.info("트레이너 정보 수정 요청, 트레이너 ID: {}", trainerUpdateRequest.getTrainerId());

        Trainer trainer = trainerRepository.findById(trainerUpdateRequest.getTrainerId())
                .orElseThrow(TrainerNotFoundException::new);

        // 정보 수정
        trainer.updateInfo(trainerUpdateRequest);
        specialtyService.setTrainerSpecialties(trainer.getTrainerId(), trainerUpdateRequest.getSpecialtyIds());

        trainerRepository.save(trainer);

        log.info("트레이너 정보 수정 완료, 트레이너 ID: {}", trainerUpdateRequest.getTrainerId());
    }

    @Override
    @Transactional(readOnly = true)
    public PageImpl<TrainerResponse> getTrainers(Pageable pageable) {
        log.info("트레이너 목록 조회 요청, 페이지 번호: {}, 페이지 크기: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        // 트레이너 페이징 조회
        var trainerPage = trainerRepository.findAll(pageable);

        // 각 트레이너에 대한 Specialty 조회 후 매핑
        List<TrainerResponse> responses = trainerPage.stream()
                .map(trainer -> {
                    List<TrainerSpecialty> specialties = trainerSpecialtyRepository
                            .findByTrainerTrainerId(trainer.getTrainerId());
                    return ResponseMapper.toTrainerResponse(trainer, specialties);
                })
                .toList();

        log.info("트레이너 목록 조회 완료, 조회 수: {}", responses.size());

        return new PageImpl<>(responses, pageable, trainerPage.getTotalElements());
    }

}
