package com.fightingkorea.platform.domain.earning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class SettleRequest {

    private Long earningId;

    private Boolean isSettled; // true 로 바꾸기

    private Boolean requestSettlement; // false로 바꾸기

    private LocalDateTime completeSettlementAt; // 현재 시간으로
}
