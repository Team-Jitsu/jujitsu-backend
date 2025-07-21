package com.fightingkorea.platform.domain.earning.service.Impl;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.earning.dto.EarningBufferResponse;
import com.fightingkorea.platform.domain.earning.dto.EarningResponse;
import com.fightingkorea.platform.domain.earning.dto.SettleRequest;
import com.fightingkorea.platform.domain.earning.entity.Earning;
import com.fightingkorea.platform.domain.earning.entity.EarningBuffer;
import com.fightingkorea.platform.domain.earning.exception.BufferEmptyException;
import com.fightingkorea.platform.domain.earning.exception.NoEarningBufferToSettleException;
import com.fightingkorea.platform.domain.earning.repository.EarningBufferRepository;
import com.fightingkorea.platform.domain.earning.repository.EarningRepository;
import com.fightingkorea.platform.domain.earning.repository.Impl.CustomEarningRepository;
import com.fightingkorea.platform.domain.earning.service.EarningBufferService;
import com.fightingkorea.platform.domain.earning.service.EarningService;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.trainer.exception.TrainerNotFoundException;
import com.fightingkorea.platform.domain.trainer.repository.TrainerRepository;
import com.fightingkorea.platform.domain.video.entity.UserVideo;
import com.fightingkorea.platform.domain.video.repository.UserVideoRepository;
import com.fightingkorea.platform.global.common.response.ResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EarningServiceImpl implements EarningService, EarningBufferService {

    private final TrainerRepository trainerRepository;
    private final EarningRepository earningRepository;
    private final CustomEarningRepository customEarningRepository;
    private final EarningBufferRepository earningBufferRepository;
    private final UserVideoRepository userVideoRepository;

    // 정산 생성(트레이너가 정산 요청)
    @Override
    public EarningResponse createEarning(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(TrainerNotFoundException::new);

       Earning earning = customEarningRepository.createEarningAndAssignToBuffers(trainerId);

       earningRepository.save(earning);

        return ResponseMapper.toEarningResponse(earning);
    }



    // 동영상 구매 시에 생성되는 정산 버퍼
    @Override
    public EarningBufferResponse createEarningBuffer(Long trainerId, Long userVideoId, Integer amount) {
       EarningBuffer buffer = EarningBuffer.createEarningBuffer(
               trainerRepository.getReferenceById(trainerId),
               userVideoRepository.getReferenceById(userVideoId), amount);
       earningBufferRepository.save(buffer);
       return ResponseMapper.toEarningBufferResponse(buffer);
    }

private Earning createAndAssignEarningsOrThrow(Long trainerId) {
    //1. 트레이너 존재 검증
    Trainer trainer = trainerRepository.findById(trainerId)
            .orElseThrow(TrainerNotFoundException::new);

    // 2. 정산할 earningBuffer가 있는지 검증
    boolean hasUnSettledBuffer = earningBufferRepository.existsByTrainerIdAndEarningIsNull(trainerId);
    if (!hasUnSettledBuffer) {
        throw new NoEarningBufferToSettleException();
    }

    // 3. earning 생성 및 버퍼에 연동
    return earningRepository.createEarningAndAssignToBuffers(trainerId);

}

    // 트레이너가 정산 요청 누름
    @Override
    public void settleEarningsByTrainer(Long trainerId) {

        Earning earning = createAndAssignEarningsOrThrow(trainerId);
        earning.setRequestSettlement(true);
        earningRepository.save(earning);
    }

    // admin이 확인 버튼 누름
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void settleEarningsByAdmin(SettleRequest req, Long trainerId) {

        Earning earning = createAndAssignEarningsOrThrow(trainerId);
        earning.setIsSettled(true);
        earning.setRequestSettlement(false);
        earning.setCompleteSettlementAt(LocalDateTime.now());
        earningRepository.save(earning);


    }

    @Override // 트레이너 or admin이 trainer의 정산리스트 알고자 할때
    public List<EarningBufferResponse> getEarningBufferList(Long trainerId) {
        return List.of();
    }

}
