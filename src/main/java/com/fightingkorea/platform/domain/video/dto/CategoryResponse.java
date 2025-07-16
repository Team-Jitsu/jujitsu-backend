package com.fightingkorea.platform.domain.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long categoryId; // 카테고리 아이디

    private String categoryName; // 카테고리 이름


}
