package com.fightingkorea.platform.domain.user.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.ValidationException;

import java.util.List;

public class InvalidCurrentPasswordException extends ValidationException {
    public InvalidCurrentPasswordException() {
        super(List.of(
                new ErrorResponse.FieldError("currentPassword", "입력하신 현재 비밀번호가 일치하지 않습니다.")
        ));
    }
}
