package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssSeatSchedule;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */
public interface ISeatScheduleService extends IService<TssSeatSchedule> {
    boolean resetTepIdAndSeatNum(List<String> delIds);

    boolean resetTepIdAndSeatNumInTemp(List<String> readyDels);

    //List<StudentSignTableVo> getSeatSchedule(List<String> ids, String name);
}
