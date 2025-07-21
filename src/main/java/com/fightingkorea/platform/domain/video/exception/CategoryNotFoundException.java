package com.fightingkorea.platform.domain.video.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.NotFoundException;

import java.util.List;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException() {

        super(List.of(
                new ErrorResponse.FieldError("category_id", "존재하지 않는 카테고리입니다.")
        ));
    }
}
