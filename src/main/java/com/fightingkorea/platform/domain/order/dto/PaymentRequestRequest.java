package com.fightingkorea.platform.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 결제 요청 생성 시 전달되는 정보
 */
@Getter
@Setter
public class PaymentRequestRequest {
    private String orderId;
    private Long userId;
    private Integer totalAmount;
    private String orderName;
    private String customerName;
    private String customerEmail;
    private List<PaymentItemDto> items;
}
