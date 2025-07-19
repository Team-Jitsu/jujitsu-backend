package com.fightingkorea.platform.domain.earning.service;

import com.fightingkorea.platform.domain.earning.dto.EarningBufferResponse;

import java.util.List;

public interface EarningBufferService {

    EarningBufferResponse createEarningBuffer(Long trainerId, Long userVideoId, Integer amount);

    EarningBufferResponse updateEarningBuffer(Long trainerId, Long userVideoId, Long earningId, Integer amount);

    List<EarningBufferResponse> getEarningBufferList(Long trainerId);
}
