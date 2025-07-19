package com.fightingkorea.platform.domain.earning.service;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.earning.dto.EarningResponse;

public interface EarningService {

    EarningResponse createEarning(CreateEarningRequest req);

    // 정산 확인 버튼 누르면 정산 처리
    EarningResponse settleAllEarnings();

    // 트레이너가 정산 요청을 누름
    void settleEarningsByTrainer(Long trainerId);
}
