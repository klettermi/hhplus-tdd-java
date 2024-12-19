package io.hhplus.tdd.common.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String name();
    HttpStatus getHttpStatus();
    String getCode();
    String getMsg();

}

