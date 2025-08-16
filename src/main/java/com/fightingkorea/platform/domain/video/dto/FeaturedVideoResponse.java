package com.fightingkorea.platform.domain.video.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class FeaturedVideoResponse {
    private Long videoId;
    private String title;
    private String description;
    private String s3Key;
    private Integer price;
    private Integer likeCount;
    private LocalDateTime uploadTime;
    private TrainerSummaryResponse trainer;
    private List<CategorySummaryResponse> categories;
}
