package com.fightingkorea.platform.domain.earning.controller;

import com.fightingkorea.platform.domain.earning.dto.EarningBufferResponse;
import com.fightingkorea.platform.domain.earning.service.EarningBufferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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

    // 트레이너 or admin이 trainer의 판매 내역을 알고자 할 때
    @GetMapping("/buffer-list")
    public Page<EarningBufferResponse> getEarningBuffers(
            @RequestParam Long trainerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return earningBufferService.getEarningBufferList(trainerId, pageable);
    }
}
