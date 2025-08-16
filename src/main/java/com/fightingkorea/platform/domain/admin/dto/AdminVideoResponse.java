package com.fightingkorea.platform.domain.admin.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminVideoResponse {
    private Long videoId;
    private String title;
    private String description;
    private Integer price;
    private String s3Key;
    private String thumbnailUrl;
    private Integer duration;
    private Long viewCount;
    private Integer likeCount;
    private LocalDateTime uploadTime;
    private String trainerName;
    private String categoryName;
    private String status;
}
