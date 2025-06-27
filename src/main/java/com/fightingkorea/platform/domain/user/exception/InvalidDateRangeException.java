package com.fightingkorea.platform.domain.user.exception;

import com.fightingkorea.platform.global.common.exception.BadRequestException;
import com.fightingkorea.platform.global.common.exception.ErrorResponse;

import java.util.List;

public class InvalidDateRangeException extends BadRequestException {
    public InvalidDateRangeException() {
        super(List.of(
                new ErrorResponse.FieldError("", "fromDate는 toDate보다 이후일 수 없습니다." )
        ));
    }
}
