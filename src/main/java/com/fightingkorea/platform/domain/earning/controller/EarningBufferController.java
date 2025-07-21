package com.fightingkorea.platform.domain.earning.controller;

import com.fightingkorea.platform.domain.earning.dto.EarningBufferResponse;
import com.fightingkorea.platform.domain.earning.service.EarningBufferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/earningBuffers")
public class EarningBufferController { //관리자용

    private final EarningBufferService earningBufferService;

    @PostMapping
    public EarningBufferResponse createEarningBuffer(
            @RequestParam Long trainerId,
            @RequestParam Long userVideoId,
            @RequestParam Integer amount) {
        return earningBufferService.createEarningBuffer(trainerId, userVideoId, amount);
    }

    // 선수가 이번달 정산 예정인 금액 확인, 관리자도 사용
    @GetMapping
    public List<EarningBufferResponse> getEarningBufferList(@RequestParam Long trainerId) {
        return earningBufferService.getEarningBufferList(trainerId);
    }
}
