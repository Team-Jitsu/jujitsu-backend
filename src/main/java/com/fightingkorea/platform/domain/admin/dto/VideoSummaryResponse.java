package com.fightingkorea.platform.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VideoSummaryResponse {
    private Long videoId;
    private String title;
}
