package io.hhplus.tdd.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400_0", "잘못된 요청입니다."),
    PARAMETER_WRONG(HttpStatus.BAD_REQUEST, "400_1", "잘못된 파라미터입니다.");

    private static final Map<String, String> CODE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(CommonErrorCode::getCode, CommonErrorCode::name)));
    private final HttpStatus status;
    private final String code;
    private final String msg;

    public static CommonErrorCode of(final String code) {
        return CommonErrorCode.valueOf(CODE_MAP.get(code));
    }

}
