package com.fightingkorea.platform.domain.earning.service;

import com.fightingkorea.platform.domain.earning.dto.EarningBufferResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EarningBufferService {

    EarningBufferResponse createEarningBuffer(Long trainerId, Long userVideoId, Integer amount);

    Page<EarningBufferResponse> getEarningBufferList(Long trainerId, Pageable pageable);
}
