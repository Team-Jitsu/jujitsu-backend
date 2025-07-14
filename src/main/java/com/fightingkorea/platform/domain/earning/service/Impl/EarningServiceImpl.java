package com.fightingkorea.platform.domain.earning.service.Impl;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.earning.dto.EarningResponse;
import com.fightingkorea.platform.domain.earning.entity.Earning;
import com.fightingkorea.platform.domain.earning.repository.EarningRepository;
import com.fightingkorea.platform.domain.earning.service.EarningService;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.trainer.exception.TrainerNotFoundException;
import com.fightingkorea.platform.domain.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EarningServiceImpl implements EarningService {

    private final TrainerRepository trainerRepository;
    private final EarningRepository earningRepository;

    @Override
    public EarningResponse createEarning(CreateEarningRequest req) {
        Trainer trainer = trainerRepository.findById(req.getTrainerId())
                .orElseThrow(TrainerNotFoundException::new);

       Earning earning =  Earning.createEarning(req, trainer);
       return earningRepository.save(earning);
    }
}
