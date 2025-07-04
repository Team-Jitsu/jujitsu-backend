package com.fightingkorea.platform.domain.trainer.controller;

import com.fightingkorea.platform.domain.trainer.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/specialty")
public class SpecialtyController {
    private final SpecialtyService specialtyService;

}