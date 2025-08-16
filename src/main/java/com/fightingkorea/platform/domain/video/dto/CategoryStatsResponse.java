package com.fightingkorea.platform.domain.video.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryStatsResponse {
    private Long categoryId;
    private String categoryName;
    private Integer courseCount;
    private Double averageRating;
    private Integer totalStudents;
    private List<FeaturedVideoResponse> featuredVideos;
}
