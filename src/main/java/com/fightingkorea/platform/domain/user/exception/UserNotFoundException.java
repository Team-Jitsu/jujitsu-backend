package com.fightingkorea.platform.domain.user.exception;


import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.NotFoundException;

import java.util.List;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super(List.of(
                new ErrorResponse.FieldError("email", "존재하지 않는 유저입니다.")
        ));
    }
}
