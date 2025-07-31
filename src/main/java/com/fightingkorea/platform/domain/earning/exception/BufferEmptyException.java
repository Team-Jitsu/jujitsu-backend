package com.fightingkorea.platform.domain.earning.exception;

import com.fightingkorea.platform.global.common.exception.ErrorResponse;
import com.fightingkorea.platform.global.common.exception.NotFoundException;

import java.util.List;

public class BufferEmptyException extends NotFoundException {
    public BufferEmptyException() {
        super(List.of(
                new ErrorResponse.FieldError("earningBuffer", "정산할 버퍼 데이터가 없습니다.")
        ));
    }
}
