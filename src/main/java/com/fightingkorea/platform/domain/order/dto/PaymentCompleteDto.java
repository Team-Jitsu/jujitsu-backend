package com.fightingkorea.platform.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 결제 완료 결과를 나타내는 DTO
 */
@Getter
@Builder
public class PaymentCompleteDto {
    private final Long paymentId;
    private final String orderId;
    private final String status;
    private final LocalDateTime completedAt;
    private final List<VideoPurchaseDto> purchasedVideos;
    private final Integer totalAmount;
}
