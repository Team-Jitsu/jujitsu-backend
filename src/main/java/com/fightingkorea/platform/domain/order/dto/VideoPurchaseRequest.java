package com.fightingkorea.platform.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoPurchaseRequest {
    private Long userId;
    private Long videoId;
    private String paymentKey; // Toss Payments에서 받은 paymentKey
    private String orderId;    // Toss Payments에서 받은 orderId (UUID)
    private int amount;        // 결제 금액
}
