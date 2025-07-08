package com.fightingkorea.platform.domain.video.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoUpdateRequest {

    private String title;

    private String description;

    private Integer price;
}
