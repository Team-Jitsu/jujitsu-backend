package com.fightingkorea.platform.domain.earning.service;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.earning.dto.EarningResponse;

public interface EarningService {
    EarningResponse createEarning(CreateEarningRequest req);
}
