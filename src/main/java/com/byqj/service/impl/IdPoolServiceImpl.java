package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssIdPoolDao;
import com.byqj.entity.TssIdPool;
import com.byqj.service.IIdPoolService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by willim on 2019/3/22.
 */
@Service(value = "idPoolService")
public class IdPoolServiceImpl extends ServiceImpl<TssIdPoolDao, TssIdPool>
        implements IIdPoolService {

    public List<TssIdPool> selectByTeStartTime(String eTime) {
        return list(new LambdaQueryWrapper<TssIdPool>()
                .eq(TssIdPool::getTeStartTime, eTime));
    }

    public synchronized TssIdPool getAndUpdateCountById(String mainExamId) {
        TssIdPool id = getById(mainExamId);
        update(new LambdaUpdateWrapper<TssIdPool>()
                .set(TssIdPool::getCount, id.getCount() + 1)
                .eq(TssIdPool::getTeId, mainExamId));
        return id;
    }
}
