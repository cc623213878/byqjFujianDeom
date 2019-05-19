package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssSeatScheduleDao;
import com.byqj.entity.TssSeatSchedule;
import com.byqj.service.ISeatScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */

@Service(value = "seatScheduleService")
public class SeatScheduleServiceImpl extends ServiceImpl<TssSeatScheduleDao, TssSeatSchedule>
        implements ISeatScheduleService {

    public boolean resetTepIdAndSeatNum(List<String> delIds) {
        return update(new LambdaUpdateWrapper<TssSeatSchedule>()
                .set(TssSeatSchedule::getTepId, "")
                .set(TssSeatSchedule::getSeatNum, 0)
                .in(TssSeatSchedule::getTepId, delIds));
    }

    public boolean resetTepIdAndSeatNumInTemp(List<String> readyDels) {
        return getBaseMapper().resetTepIdAndSeatNumInTemp(readyDels);
    }

//    public List<StudentSignTableVo> getSeatSchedule(List<String> ids, String name) {
//        Map map = Maps.newHashMap();
//        map.put("xm", name);
//        return list(new LambdaQueryWrapper<TssSeatSchedule>()
//                .ne(TssSeatSchedule::getSeatNum, "0")
//                .in(TssSeatSchedule::getTepId, ids)
//                .allEq(map, false));
//    }
}
