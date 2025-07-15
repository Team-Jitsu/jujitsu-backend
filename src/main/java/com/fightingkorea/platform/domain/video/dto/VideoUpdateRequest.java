package com.fightingkorea.platform.domain.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoUpdateRequest {

    private String title;

    private String description;

    private Integer price;

    private List<Long> categoryIds;
}
