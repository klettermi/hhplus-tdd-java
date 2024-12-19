package io.hhplus.tdd.point.validator;

import io.hhplus.tdd.common.code.CommonErrorCode;
import io.hhplus.tdd.common.exception.CustomException;

import static io.hhplus.tdd.point.service.impl.PointServiceImpl.MAX_POINT;

public class PointValidator {
    public static void validate(Long point) {
        if (point == null) {
            throw new CustomException(CommonErrorCode.PARAMETER_WRONG, "Point can not be null.");
        }
        if (point < 100) {
            throw new CustomException(CommonErrorCode.PARAMETER_WRONG, "Point can not be less than 100.");
        }
        if (point > MAX_POINT) {
            throw new CustomException(CommonErrorCode.PARAMETER_WRONG, "Point can not be greater than " + MAX_POINT + ".");
        }
    }
}
