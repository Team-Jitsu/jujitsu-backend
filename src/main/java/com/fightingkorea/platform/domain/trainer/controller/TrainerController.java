package com.fightingkorea.platform.domain.trainer.controller;

import com.fightingkorea.platform.domain.trainer.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainers")
public class TrainerController {
    private final TrainerService trainerService;
}
