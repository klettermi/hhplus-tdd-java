package io.hhplus.tdd.common.exception;

import io.hhplus.tdd.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final String code;
    private final String msg;
    private final String description;

    public CustomException(String code, String msg, String description) {
        super(description);
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.description = errorCode.getMsg();
    }

    public CustomException(ErrorCode errorCode, String customMsg) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = customMsg;
        this.description = customMsg;
    }


}
