package com.fightingkorea.platform.domain.video.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VideoUploadRequest {

    @NotNull
    private Long trainerId; // 선수 아이디

    @NotNull
    private String title; // 동영상 제목

    @NotNull
    private String url; // 영상 파일 경로

    private String description; // 설명

    private Integer price; // 가격
}
