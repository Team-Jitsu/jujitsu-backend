package com.fightingkorea.platform.domain.trainer.exception;

import com.fightingkorea.platform.global.common.exception.ConflictException;
import com.fightingkorea.platform.global.common.exception.ErrorResponse;

import java.util.List;

public class SpecialtyConflictException extends ConflictException {
    public SpecialtyConflictException() {
        super(List.of(
                new ErrorResponse.FieldError("specialty_name", "이미 존재하는 전문분야 입니다.")
        ));
    }
}