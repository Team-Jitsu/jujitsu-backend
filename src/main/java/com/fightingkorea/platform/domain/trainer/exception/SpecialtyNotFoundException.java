package com.fightingkorea.platform.domain.trainer.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.NotFoundException;

import java.util.List;

public class SpecialtyNotFoundException extends NotFoundException {
    public SpecialtyNotFoundException() {
        super(List.of(
                new ErrorResponse.FieldError("specialty_id", "존재하지 않는 전문분야입니다.")
        ));
    }
}