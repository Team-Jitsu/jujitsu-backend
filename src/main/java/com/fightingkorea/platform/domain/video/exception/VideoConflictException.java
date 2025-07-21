package com.fightingkorea.platform.domain.video.exception;

import com.fightingkorea.platform.global.common.exception.ConflictException;
import com.fightingkorea.platform.global.common.exception.ErrorResponse;

import java.util.List;

public class VideoConflictException extends ConflictException {
    public VideoConflictException() {

        super(List.of(
                new ErrorResponse.FieldError("title", "같은 제목의 강의가 이미 존재합니다.")
        ));
    }
}
