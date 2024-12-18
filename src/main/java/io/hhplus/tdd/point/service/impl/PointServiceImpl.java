package io.hhplus.tdd.point.service.impl;

import io.hhplus.tdd.common.code.CommonErrorCode;
import io.hhplus.tdd.common.exception.CustomException;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.entity.PointHistory;
import io.hhplus.tdd.point.entity.UserPoint;
import io.hhplus.tdd.point.service.PointService;
import io.hhplus.tdd.point.type.TransactionType;
import io.hhplus.tdd.point.validator.PointValidator;
import io.hhplus.tdd.point.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    public static final Long MAX_POINT = 1000000L;

    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;

    @Override
    public UserPoint getUserPoint(long id) {
        UserValidator.validate(id);
        return userPointTable.selectById(id);
    }

    @Override
    public List<PointHistory> getPointHistory(long id) {
        UserValidator.validate(id);
        return pointHistoryTable.selectAllByUserId(id);
    }

    @Override
    public UserPoint chargePoint(long id, long amount) {
        UserValidator.validate(id);
        PointValidator.validate(amount);

        long totalPoint = userPointTable.selectById(id).point() + amount;

        if (totalPoint > MAX_POINT) {
            throw new CustomException(CommonErrorCode.BAD_REQUEST, "Maximum point exceeded");
        }

        UserPoint userpoint = userPointTable.insertOrUpdate(id, totalPoint);
        pointHistoryTable.insert(id, amount, TransactionType.CHARGE, currentTimeMillis());

        return userpoint;
    }

    @Override
    public UserPoint usePoint(long id, long amount) {
        UserValidator.validate(id);
        PointValidator.validate(amount);

        long balance = userPointTable.selectById(id).point() - amount;
        if (balance < 0) {
            throw new CustomException(CommonErrorCode.BAD_REQUEST, "The point balance is insufficient");
        }

        UserPoint userPoint = userPointTable.insertOrUpdate(id, balance);
        pointHistoryTable.insert(id, amount, TransactionType.USE, currentTimeMillis());

        return userPoint;
    }
}
