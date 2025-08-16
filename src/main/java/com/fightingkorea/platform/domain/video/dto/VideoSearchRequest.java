package com.fightingkorea.platform.domain.video.dto;

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
public class VideoSearchRequest {
    private Long categoryId;
    private Long trainerId;
    private String search;
    private Integer minPrice;
    private Integer maxPrice;
}

