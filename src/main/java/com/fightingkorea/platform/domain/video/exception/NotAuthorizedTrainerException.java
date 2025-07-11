package com.fightingkorea.platform.domain.video.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.ForbiddenException;
import com.fightingkorea.platform.global.common.exception.ValidationException;

import java.util.List;

public class NotAuthorizedTrainerException extends ForbiddenException {
    public NotAuthorizedTrainerException() {
        super(List.of(
                new ErrorResponse.FieldError("trainerId", "해당 강의의 업로더가 아닙니다.")
        ));
    }
}
