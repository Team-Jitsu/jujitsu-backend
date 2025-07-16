package com.fightingkorea.platform.domain.video.exception;

import com.fightingkorea.platform.global.common.exception.ConflictException;
import com.fightingkorea.platform.global.common.exception.ErrorResponse;

import java.util.List;

public class CategoryConflictException extends ConflictException {
    public CategoryConflictException() {

        super(List.of(
                new ErrorResponse.FieldError("category_name", "이미 존재하는 카테고리 입니다.")
        ));
    }
}
