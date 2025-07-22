package com.fightingkorea.platform.domain.earning.controller;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.earning.dto.EarningBufferResponse;
import com.fightingkorea.platform.domain.earning.dto.EarningResponse;
import com.fightingkorea.platform.domain.earning.service.EarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/earning-list")
    public Page<EarningResponse> getEarningBuffers(
            @RequestParam Long trainerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "requestSettlementAt"));

        return earningService.getEarningList(trainerId,pageable);
    }


}
