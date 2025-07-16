package com.fightingkorea.platform.domain.video.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class VideoCategoryId implements Serializable {
    private Long videoId;
    private Long categoryId;
}
