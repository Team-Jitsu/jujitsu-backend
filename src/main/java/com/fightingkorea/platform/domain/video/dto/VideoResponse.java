package com.fightingkorea.platform.domain.video.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.Instant;

@Getter
@Builder
public class VideoResponse {
    private Long videoId;
    private String title;
    private String description;
    private Integer price;
    private String s3Key;
    private String playUrl;     // GET presigned URL
    private Instant urlExpires; // 만료시각
}
