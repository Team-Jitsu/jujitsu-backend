package com.fightingkorea.platform.domain.earning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EarningBufferResponse {

    private Long bufferId;

    private Long trainerId;

    private Long userVideoId;

    private Long earningId;

    private Integer amount;

    private LocalDateTime createdAt;
}