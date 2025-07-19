package com.fightingkorea.platform.domain.earning.controller;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.earning.dto.EarningResponse;
import com.fightingkorea.platform.domain.earning.service.EarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/earnings")
public class EarningController { //관리자용 (정산 내역 조회, 정산 확정 처리)

    private final EarningService earningService;

    @PostMapping
    public EarningResponse createEarning(CreateEarningRequest req){
        return earningService.createEarning(req);
    }

    @PutMapping("/settle") // 관리자가 수동으로 확정 버튼 누르는 시나리오
    public EarningResponse settleEarnings(){
        return earningService.settleAllEarnings();
    }
}
