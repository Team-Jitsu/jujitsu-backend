package com.fightingkorea.platform.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 결제 상태 정보를 나타내는 DTO
 */
@Getter
@Builder
public class PaymentStatusDto {
    private final String paymentKey;
    private final String orderId;
    private final String status;
    private final Integer totalAmount;
    private final LocalDateTime approvedAt;
    private final String method;
    private final String cardCompany;
    private final String cardNumber;
    private final String failureCode;
    private final String failureMessage;
}
