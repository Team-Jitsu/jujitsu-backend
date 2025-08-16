package com.fightingkorea.platform.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 결제 요청 결과를 나타내는 DTO
 */
@Getter
@Builder
public class PaymentRequestDto {
    private final String orderId;
    private final String tossPaymentKey;
    private final String tossOrderId;
    private final String paymentUrl;
    private final String status;
    private final Integer amount;
    private final LocalDateTime expiresAt;
}
