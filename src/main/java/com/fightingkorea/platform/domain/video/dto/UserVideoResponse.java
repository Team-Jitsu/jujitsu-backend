package com.fightingkorea.platform.domain.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserVideoResponse {

    private Long videoId; // 비디오 아이디

    private String title; // 비디오 제목

    private Integer purchasePrice; // 구매 당시 가격

    private LocalDateTime purchasedAt; // 구매 일시


}
