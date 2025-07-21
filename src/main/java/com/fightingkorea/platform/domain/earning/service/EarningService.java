package com.fightingkorea.platform.domain.earning.service;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.earning.dto.EarningResponse;
import com.fightingkorea.platform.domain.earning.dto.SettleRequest;

public interface EarningService {

    EarningResponse createEarning(Long trainerId);

    // 트레이너가 정산 요청을 누름
    void settleEarningsByTrainer(Long trainerId);

    // admin이 확인 버튼 누름
    void settleEarningsByAdmin(SettleRequest req, Long trainerId);
}
