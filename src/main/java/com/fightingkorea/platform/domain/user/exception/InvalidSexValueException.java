package com.fightingkorea.platform.domain.user.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.ValidationException;

import java.util.List;

public class InvalidSexValueException extends ValidationException {
    public InvalidSexValueException() {
        super(List.of(
                new ErrorResponse.FieldError("sex", "유효하지 않은 성별 값입니다.")
        ));
    }
}
