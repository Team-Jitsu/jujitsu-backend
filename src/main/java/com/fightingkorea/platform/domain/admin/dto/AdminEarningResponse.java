package com.fightingkorea.platform.domain.admin.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminEarningResponse {
    private Long earningId;
    private Long trainerId;
    private String trainerName;
    private Long totalAmount;
    private boolean isSettled;
    private boolean requestSettlement;
    private LocalDateTime requestSettlementAt;
    private LocalDateTime createdAt;
    private List<EarningDetailResponse> earningDetails;
}
