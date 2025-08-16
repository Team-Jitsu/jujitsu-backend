package com.fightingkorea.platform.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 요청 본문으로 전달되는 결제 실패 정보
 */
@Getter
@Setter
public class PaymentFailRequest {
    /** Toss Payments에서 발급한 주문 ID */
    private String tossOrderId;
    /** 실패 사유 코드 (선택) */
    private String errorCode;
    /** 실패 상세 메시지 */
    private String errorMessage;
}
