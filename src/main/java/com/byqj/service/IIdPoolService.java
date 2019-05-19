package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssIdPool;

import java.util.List;

/**
 * Created by willim on 2019/3/22.
 */
public interface IIdPoolService extends IService<TssIdPool> {
    List<TssIdPool> selectByTeStartTime(String eTime);

    TssIdPool getAndUpdateCountById(String mainExamId);
}
