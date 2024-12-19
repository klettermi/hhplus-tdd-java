package io.hhplus.tdd.point;

import io.hhplus.tdd.common.exception.CustomException;
import io.hhplus.tdd.point.validator.PointValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.hhplus.tdd.point.service.impl.PointServiceImpl.MAX_POINT;
import static org.assertj.core.api.Assertions.*;

public class PointValidateTest {

    /**
     * Point Validation
     */

    @Test
    @DisplayName("Validate Normal Point")
    void validateNormalPoint() {
        long point = new Random().nextLong(MAX_POINT);

        PointValidator.validate(point);

        assertThat(point)
                .isNotNull()
                .isGreaterThan(0)
                .isLessThanOrEqualTo(MAX_POINT);
    }

    @Test
    @DisplayName("Validate Null Point")
    void validateNullPoint() {
        Long point = null;

        assertThatThrownBy(() -> PointValidator.validate(point)).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("Validate Negative Point")
    void validateNegativePoint() {
        Long point = -1L;
        assertThatThrownBy(() -> PointValidator.validate(point)).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("Validate Max Point")
    void validateMaxPoint() {
        Long point = MAX_POINT + 1L;
        assertThatThrownBy(() -> PointValidator.validate(point)).isInstanceOf(CustomException.class);
    }
}
