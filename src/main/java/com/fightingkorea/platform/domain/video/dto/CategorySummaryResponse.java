package com.fightingkorea.platform.domain.video.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategorySummaryResponse {
    private Long categoryId;
    private String categoryName;
}
