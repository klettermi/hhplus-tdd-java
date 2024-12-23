package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.entity.PointHistory;
import io.hhplus.tdd.point.entity.UserPoint;

import java.util.List;

public interface PointService {
    UserPoint getUserPoint(long id);

    List<PointHistory> getPointHistory(long id);

    UserPoint chargePoint(long id, long amount);

    UserPoint usePoint(long id, long amount);
}
