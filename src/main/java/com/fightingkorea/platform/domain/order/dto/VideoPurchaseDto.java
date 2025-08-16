package com.fightingkorea.platform.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 결제 완료 시 구매된 강의 정보를 나타내는 DTO
 */
@Getter
@Builder
public class VideoPurchaseDto {
    private final Long purchaseId;
    private final Long videoId;
    private final String title;
    private final Integer price;
}
