package io.hhplus.tdd.point.validator;

import io.hhplus.tdd.common.code.CommonErrorCode;
import io.hhplus.tdd.common.exception.CustomException;

public class UserValidator {
    public static void validate(Long userId) {
        if (userId == null) {
            throw new CustomException(CommonErrorCode.PARAMETER_WRONG, "UserId can not be null");
        }
        if (userId < 0) {
            throw new CustomException(CommonErrorCode.PARAMETER_WRONG, "UserId can not be negative");
        }
    }
}
