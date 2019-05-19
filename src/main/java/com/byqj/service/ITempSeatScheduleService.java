package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssTempSeatSchedule;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */
public interface ITempSeatScheduleService extends IService<TssTempSeatSchedule> {

    boolean resetTepIdAndSeatNumInTemp(List<String> readyDelIds);

    boolean copyToMainTableByTeId(String mainExId);

    boolean removeByTeId(String mainExId);
}
