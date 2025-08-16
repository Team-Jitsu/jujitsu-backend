package com.fightingkorea.platform.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchRequest {
    private String q;
    private String type;
    private Long categoryId;
    private Long specialtyId;
    private Integer minPrice;
    private Integer maxPrice;
}
