package com.fightingkorea.platform.domain.admin.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EarningDetailResponse {
    private Long bufferId;
    private Long amount;
    private LocalDateTime createdAt;
    private VideoSummaryResponse video;
}
