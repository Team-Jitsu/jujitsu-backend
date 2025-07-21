package com.fightingkorea.platform.domain.video.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.NotFoundException;

import java.util.List;

public class UserVideoListNotFoundException extends NotFoundException {
    public UserVideoListNotFoundException() {
        super(List.of(
                new ErrorResponse.FieldError("", "해당 유저가 구매한 강의가 없습니다.")
                )
        );
    }
}
