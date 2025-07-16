package com.fightingkorea.platform.domain.earning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateEarningRequest {

    private Long trainerId; // 선수 아이디

    private Long totalAmount; // 수익 합계
}
