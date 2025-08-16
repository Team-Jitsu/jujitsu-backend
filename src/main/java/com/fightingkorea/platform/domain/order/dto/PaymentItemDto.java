package com.fightingkorea.platform.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 개별 결제 상품 정보를 담는 DTO
 */
@Getter
@Setter
public class PaymentItemDto {
    /** 구매할 강의 ID */
    private Long videoId;
    /** 강의 제목 */
    private String title;
    /** 강의 가격 */
    private Integer price;
}
