package io.hhplus.tdd.user;

import io.hhplus.tdd.common.exception.CustomException;
import io.hhplus.tdd.point.validator.UserValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.*;

public class UserValidationTest {

    @Test
    @DisplayName("Validate User Id - Success")
    void validateUserId() {
        long userId = new Random().nextLong();

        UserValidator.validate(userId);

        assertThat(userId)
                .isGreaterThan(0)
                .isNotNull();
    }

    @Test
    @DisplayName("Validate Null User Id - Fail")
    void validateNullUserId() {
        Long userId = null;
        assertThatThrownBy(() -> UserValidator.validate(userId)).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("Validate Negative User Id - Fail")
    void validateNegativeUserId() {
        Long userId = -1L;
        assertThatThrownBy(() -> UserValidator.validate(userId)).isInstanceOf(CustomException.class);
    }
}
