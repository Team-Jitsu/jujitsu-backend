package com.fightingkorea.platform.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 결제 취소 요청 정보를 담는 DTO
 */
@Getter
@Setter
public class PaymentCancelRequest {
    /** 취소 사유 */
    private String cancelReason;
    /** 취소 금액 (부분 취소 시 지정, 없으면 전체 취소) */
    private Integer cancelAmount;
}
