package com.fightingkorea.platform.domain.earning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateEarningBufferRequest {

    private Long trainerId;

    private Long userVideoId;

    private Long earningId;

    private Integer amount;
}
