package com.fightingkorea.platform.domain.earning.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.NotFoundException;

import java.util.List;

public class NoEarningBufferToSettleException extends NotFoundException {
    public NoEarningBufferToSettleException() {
        super(List.of(
                new ErrorResponse.FieldError("earningBuffer", "정산할 EarningBuffer가 존재하지 않습니다.")
        ));
    }
}
