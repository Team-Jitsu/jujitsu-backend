package com.fightingkorea.platform.domain.video.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VideoUploadMultipartRequest {

    @NotNull
    private Long trainerId; // 선수 아이디

    @NotNull
    private String title; // 동영상 제목

    private String description; // 설명

    private Integer price; // 가격

    private List<Long> categoryIds; // 카테고리 아이디 리스트
}