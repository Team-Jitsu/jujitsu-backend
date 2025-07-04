package com.fightingkorea.platform.domain.trainer.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.NotFoundException;

import java.util.List;

public class TrainerNotFoundException extends NotFoundException {
    public TrainerNotFoundException() {
        super(List.of(
                new ErrorResponse.FieldError("trainer_id", "존재하지 않는 선수입니다.")
        ));
    }
}
