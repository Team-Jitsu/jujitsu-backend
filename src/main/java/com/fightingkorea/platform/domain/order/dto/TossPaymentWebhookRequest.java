package com.fightingkorea.platform.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Toss Payments webhook에서 전달하는 결제 완료 요청 DTO
 */
@Getter
@Setter
public class TossPaymentWebhookRequest {
    private String paymentKey;
    private String orderId;
    private Integer totalAmount;
    private String status;
    private String approvedAt;
    private String method;
    private String cardCompany;
    private String cardNumber;
    private String failureCode;
    private String failureMessage;
}
