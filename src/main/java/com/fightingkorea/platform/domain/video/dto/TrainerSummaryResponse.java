package com.fightingkorea.platform.domain.video.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrainerSummaryResponse {
    private Long trainerId;
    private String nickname;
    private String bio;
}
