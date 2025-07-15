package com.fightingkorea.platform.domain.earning.controller;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.earning.dto.EarningResponse;
import com.fightingkorea.platform.domain.earning.service.EarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/earnings")
public class EarningController {

    private final EarningService earningService;

    @PostMapping
    public EarningResponse createEarning(CreateEarningRequest req){
        return earningService.createEarning(req);
    }
}
