package com.fightingkorea.platform.domain.video.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseSearchRequest {
    private Long categoryId; // 카테고리 ID
    private String search;   // 제목 검색어
    private String sortBy;   // purchaseDate, title
    private String sortOrder; // asc, desc
}
