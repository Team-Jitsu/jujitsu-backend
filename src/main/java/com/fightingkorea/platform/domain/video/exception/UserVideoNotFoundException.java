package com.fightingkorea.platform.domain.video.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.NotFoundException;

import java.util.List;

public class UserVideoNotFoundException extends NotFoundException {
    public UserVideoNotFoundException()
    {
      super(List.of(
            new ErrorResponse.FieldError("userVideo_id", "존재하지 않는 동영상 구매내역입니다.")
    ));

    }
}
