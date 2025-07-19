package com.fightingkorea.platform.domain.earning.service.Impl;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.earning.dto.EarningResponse;
import com.fightingkorea.platform.domain.earning.entity.Earning;
import com.fightingkorea.platform.domain.earning.entity.EarningBuffer;
import com.fightingkorea.platform.domain.earning.repository.EarningBufferRepository;
import com.fightingkorea.platform.domain.earning.repository.EarningRepository;
import com.fightingkorea.platform.domain.earning.repository.Impl.CustomEarningRepository;
import com.fightingkorea.platform.domain.earning.service.EarningService;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.trainer.exception.TrainerNotFoundException;
import com.fightingkorea.platform.domain.trainer.repository.TrainerRepository;
import com.fightingkorea.platform.global.common.response.ResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EarningServiceImpl implements EarningService {

    private final TrainerRepository trainerRepository;
    private final EarningRepository earningRepository;
    private final CustomEarningRepository customEarningRepository;
    private final ResponseMapper responseMapper;
    private final EarningBufferRepository earningBufferRepository;

    @Override
    public EarningResponse createEarning(CreateEarningRequest req) {
        Trainer trainer = trainerRepository.findById(req.getTrainerId())
                .orElseThrow(TrainerNotFoundException::new);

       Earning earning = customEarningRepository.createEarningAndAssignToBuffers(trainer.getTrainerId());

       earningRepository.save(earning); // EarningResponse-mapper만들기

        return ResponseMapper.toEarningResponse(earning);
    }


//    // 정산버튼 누르면 정산처리(earningBuffer에 있는 데이터를 읽어서 Earning테이블에 저장하고
//    // EarningBuffer에서는 삭제하거나 상태값을 갱신)
//    @Transactional
//    @Override
//    public EarningResponse settleAllEarnings() {
//
//        // 1. 아직 정산되지 않은 EarningBuffer 조회
//        List<EarningBuffer> unsettledBuffers = earningBufferRepository.findByEarningIsNull();
//        return null;
//    }


    @Override
    public void settleEarningsByTrainer(Long trainerId) {
      Trainer trainer =  trainerRepository.findById(trainerId).orElseThrow(TrainerNotFoundException::new);

      // 아직 정산되지 않은 버퍼 조회
        List<EarningBuffer> buffers = earningBufferRepository.findByTrainerAndEarningIsNull(trainer);

        if (buffers.isEmpty()){
            throw new BufferEmptyException();
        }

        Long totalAmount = buffers.stream()
                .mapToLong(EarningBuffer::getAmount)
                .sum();

        // Earning 생성
        Earning earning = Earning.builder()
                .trainer(trainer)
                .totalAmount(totalAmount)
                .isSettled(false) //이거 생각해보기
                .completeSettlementAt() //이거 생각해보기
                .build();

        earningRepository.save(earning);

        // 각 버퍼에 earning 연결
        for (EarningBuffer buffer : buffers){
            buffer.setEarning(earning); // 연관관계 설정 (버퍼에서 Earning을 참조)
        }

    }

}
