package com.fightingkorea.platform.domain.earning.controller;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.earning.dto.EarningResponse;
import com.fightingkorea.platform.domain.earning.service.EarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/earnings")
public class EarningController { //관리자용 (정산 내역 조회, 정산 확정 처리)

    private final EarningService earningService;

    @PostMapping
    public EarningResponse createEarning(@RequestParam Long trainerId){
        return earningService.createEarning(trainerId);
    }

}
