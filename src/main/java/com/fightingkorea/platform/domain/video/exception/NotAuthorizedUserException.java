package com.fightingkorea.platform.domain.video.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.ForbiddenException;

import java.util.List;

public class NotAuthorizedUserException extends ForbiddenException {
    public NotAuthorizedUserException()
    {
        super(List.of(
                new ErrorResponse.FieldError("userId", "해당 구매내역 삭제 권한이 없습니다.")
        ));
    }
}
