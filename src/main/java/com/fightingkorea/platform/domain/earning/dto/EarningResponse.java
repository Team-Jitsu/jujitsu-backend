package com.fightingkorea.platform.domain.earning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EarningResponse {

    private Long earningId;

    private Long trainerId;

    private Long totalAmount;

    private Boolean isSettled;

    private Boolean requestSettlement;
}
