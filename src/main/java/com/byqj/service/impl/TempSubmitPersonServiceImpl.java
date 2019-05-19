package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssTempSubmitPersonDao;
import com.byqj.entity.TssTempSubmitPerson;
import com.byqj.service.ITempSubmitPersonService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by willim on 2019/3/22.
 */

@Service(value = "tempSubmitPersonService")
public class TempSubmitPersonServiceImpl extends ServiceImpl<TssTempSubmitPersonDao, TssTempSubmitPerson>
        implements ITempSubmitPersonService {

    public boolean resetTeIdByTeIdsInTemp(List<String> readyDelIds) {
        return update(new LambdaUpdateWrapper<TssTempSubmitPerson>()
                .set(TssTempSubmitPerson::getTeId, "")
                .in(TssTempSubmitPerson::getTeId, readyDelIds));
    }

    public boolean copyToMainTableByTeId(String mainExId) {
        return getBaseMapper().copyToMainTableByTeId(mainExId) >= 0;
    }

    public boolean removeByTeId(String mainExId) {
        return remove(new LambdaQueryWrapper<TssTempSubmitPerson>()
                .eq(TssTempSubmitPerson::getTeId, mainExId));
    }

    @Override
    public int countByUserListAndExamId(String teId, Collection<String> userId) {
        return count(new LambdaQueryWrapper<TssTempSubmitPerson>().eq(TssTempSubmitPerson::getTeId, teId).in(TssTempSubmitPerson::getId, userId));
    }

    @Override
    public List<TssTempSubmitPerson> batchSelectPartById(List<String> ids) {
        return list(new LambdaQueryWrapper<TssTempSubmitPerson>()
                .select(TssTempSubmitPerson::getCollegeId, TssTempSubmitPerson::getWorkCode, TssTempSubmitPerson::getName)
                .in(TssTempSubmitPerson::getId, ids));
    }
}
