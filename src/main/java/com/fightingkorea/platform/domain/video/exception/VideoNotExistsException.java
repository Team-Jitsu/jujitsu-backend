package com.fightingkorea.platform.domain.video.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.NotFoundException;

import java.util.List;

public class VideoNotExistsException extends NotFoundException {
    public VideoNotExistsException() {
        super(List.of(
                new ErrorResponse.FieldError("videoId", "강의가 존재하지 않습니다.")
                )
        );
    }
}
