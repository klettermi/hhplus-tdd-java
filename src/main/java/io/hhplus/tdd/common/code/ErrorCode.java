package io.hhplus.tdd.common.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String name();

    String getCode();

    String getMsg();

}

