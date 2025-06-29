package com.fightingkorea.platform.domain.user.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.ValidationException;

import java.util.List;

public class ForbiddenWordInNicknameException extends ValidationException {
    public ForbiddenWordInNicknameException() {
        super(List.of(
                new ErrorResponse.FieldError("nickname", "비속어는 닉네임에 포함될 수 없습니다." )
        ));
    }
}
