package io.hhplus.tdd.point.service.impl;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.entity.PointHistory;
import io.hhplus.tdd.point.entity.UserPoint;
import io.hhplus.tdd.point.service.PointService;
import io.hhplus.tdd.point.validator.PointValidator;
import io.hhplus.tdd.point.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    public static final Long MAX_POINT = 1000000L;

    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;

    @Override
    public UserPoint getUserPoint(long id) {
        return userPointTable.selectById(id);
    }

    @Override
    public List<PointHistory> getPointHistory(long id) {
        return pointHistoryTable.selectAllByUserId(id);
    }

}
