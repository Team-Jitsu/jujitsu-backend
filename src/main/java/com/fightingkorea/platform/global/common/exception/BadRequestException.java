package com.fightingkorea.platform.global.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class BadRequestException extends BaseException {
    public BadRequestException(List<ErrorResponse.FieldError> errors) {
        super(HttpStatus.BAD_REQUEST.getReasonPhrase(), "잘못된 요청입니다.", HttpStatus.BAD_REQUEST, errors);
    }
}
