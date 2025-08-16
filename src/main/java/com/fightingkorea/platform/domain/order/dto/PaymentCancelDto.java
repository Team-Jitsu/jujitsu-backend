package com.fightingkorea.platform.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 결제 취소 결과를 나타내는 DTO
 */
@Getter
@Builder
public class PaymentCancelDto {
    private final String paymentKey;
    private final String orderId;
    private final String status;
    private final LocalDateTime canceledAt;
    private final String cancelReason;
    private final Integer cancelAmount;
}
