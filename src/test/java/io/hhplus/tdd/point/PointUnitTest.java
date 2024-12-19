package io.hhplus.tdd.point;

import io.hhplus.tdd.common.exception.CustomException;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.entity.PointHistory;
import io.hhplus.tdd.point.entity.UserPoint;
import io.hhplus.tdd.point.service.PointService;
import io.hhplus.tdd.point.service.impl.PointServiceImpl;
import io.hhplus.tdd.point.type.TransactionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.hhplus.tdd.point.service.impl.PointServiceImpl.MAX_POINT;
import static java.lang.System.currentTimeMillis;
import static org.assertj.core.api.Assertions.*;

public class PointUnitTest {

    private PointService pointService;

    private UserPointTable userPointTable;
    private PointHistoryTable pointHistoryTable;

    @BeforeEach
    void setUp() {
        userPointTable = new UserPointTable();
        pointHistoryTable = new PointHistoryTable();
        pointService = new PointServiceImpl(userPointTable, pointHistoryTable);
    }

    /**
     * Inquiry User Point Test
     */
    @Test
    @DisplayName("Inquiry User Point - Success")
    void inquiryUserPoint() {
        userPointTable.insertOrUpdate(1L, 100);

        UserPoint userPoint = userPointTable.selectById(1L);

        assertThat(userPoint.point()).isEqualTo(100);
    }

    /**
     * Inquiry User Point History Test
     */
    @Test
    @DisplayName("Inquiry User Point History - Success")
    void inquiryUserPointHistory() {
        pointHistoryTable.insert(1L, 100, TransactionType.CHARGE, currentTimeMillis());
        pointHistoryTable.insert(1L, 200, TransactionType.CHARGE, currentTimeMillis());
        pointHistoryTable.insert(1L, 300, TransactionType.USE, currentTimeMillis());

        List<PointHistory> pointHistoryList = pointService.getPointHistory(1L);

        assertThat(pointHistoryList.size()).isEqualTo(3);
    }

    /**
     * Charge User Point Test
     */
    @Test
    @DisplayName("Charge User Point - Success")
    void chargeUserPoint() {
        userPointTable.insertOrUpdate(1L, 100);

        UserPoint chargePoint = pointService.chargePoint(1L, 1000);

        assertThat(chargePoint.point()).isEqualTo(1100);
    }

    @Test
    @DisplayName("Try Many Requests To Charge User Point")
    void tryManyRequestToChargeUserPoint() {
        userPointTable.insertOrUpdate(1L, 100);

        UserPoint chargePoint1 = pointService.chargePoint(1L, 1000);
        UserPoint chargePoint2 = pointService.chargePoint(1L, 10000);
        UserPoint chargePoint3 = pointService.chargePoint(1L, 100000);

        UserPoint userPoint = pointService.getUserPoint(1L);

        assertThat(userPoint.point()).isEqualTo(111100L);
    }

    @Test
    @DisplayName("Charge Max Point - Fail")
    void chargeMaxPoint() {
        userPointTable.insertOrUpdate(1L, 100);

        assertThatThrownBy(() -> pointService.chargePoint(1L, MAX_POINT)).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("Charge Min Point - Fail")
    void chargeMinPoint() {
        userPointTable.insertOrUpdate(1L, 100);

        assertThatThrownBy(() -> pointService.chargePoint(1L, 10)).isInstanceOf(CustomException.class);
    }

    /**
     * Use Point Test
     */
    @Test
    @DisplayName("Use Point - Success")
    void usePoint() {
        userPointTable.insertOrUpdate(1L, 1000);

        UserPoint usePoint = pointService.usePoint(1L, 100);

        UserPoint userPoint = pointService.getUserPoint(1L);

        assertThat(userPoint.point()).isEqualTo(900L);
    }

    @Test
    @DisplayName("Try Many Requests To Use Point - Success")
    void tryManyRequestToUsePoint() {
        userPointTable.insertOrUpdate(1L, 100);

        UserPoint usePoint1 = pointService.usePoint(1L, 10);
        UserPoint usePoint2 = pointService.usePoint(1L, 20);
        UserPoint usePoint3 = pointService.usePoint(1L, 30);

        UserPoint userPoint = pointService.getUserPoint(1L);

        assertThat(userPoint.point()).isEqualTo(40L);
    }

    @Test
    @DisplayName("Try Requests To Use Point More Than Balance - Fail")
    void tryRequestToUsePointMoreThanBalance() {
        userPointTable.insertOrUpdate(1L, 100);

        assertThatThrownBy(() -> pointService.usePoint(1L, 200)).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("Try Many Requests To Use Point More Than Balance - Fail")
    void tryNegativeRequestToUsePoint() {
        userPointTable.insertOrUpdate(1L, 1000);

        UserPoint userPoint1 = pointService.usePoint(1L, 100);

        assertThatThrownBy(() -> pointService.usePoint(1L, 1000)).isInstanceOf(CustomException.class);
    }


}
