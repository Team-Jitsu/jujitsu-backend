package com.fightingkorea.platform.domain.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponse {

    private Long videoId; // 비디오 ID

    private Long trainerId; // 선수(업로더) 아이디

    private String title; // 동영상 제목

    private LocalDateTime uploadTime; // 업로드 시간

}
