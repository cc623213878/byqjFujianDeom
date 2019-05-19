package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssTempSeatScheduleDao;
import com.byqj.entity.TssTempSeatSchedule;
import com.byqj.service.ITempSeatScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */

@Service(value = "tempSeatScheduleService")
public class TempSeatScheduleServiceImpl extends ServiceImpl<TssTempSeatScheduleDao, TssTempSeatSchedule>
        implements ITempSeatScheduleService {

    public boolean resetTepIdAndSeatNumInTemp(List<String> readyDelIds) {
        return update(new LambdaUpdateWrapper<TssTempSeatSchedule>()
                .set(TssTempSeatSchedule::getTepId, "")
                .set(TssTempSeatSchedule::getSeatNum, 0)
                .in(TssTempSeatSchedule::getTepId, readyDelIds));
    }

    public boolean copyToMainTableByTeId(String mainExId) {
        return getBaseMapper().copyToMainTableByTeId(mainExId) >= 0;
    }

    @Override
    public boolean removeByTeId(String mainExId) {
        return remove(new LambdaQueryWrapper<TssTempSeatSchedule>()
                .eq(TssTempSeatSchedule::getTeId, mainExId));
    }
}
