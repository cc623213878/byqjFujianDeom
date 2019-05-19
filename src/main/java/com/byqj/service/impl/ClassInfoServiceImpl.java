package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssClassInfoDao;
import com.byqj.entity.TssClassInfo;
import com.byqj.service.IClassInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */
@Service(value = "classInfoService")
public class ClassInfoServiceImpl extends ServiceImpl<TssClassInfoDao, TssClassInfo>
        implements IClassInfoService {

    public List<TssClassInfo> selectByPlace(List<Integer> place) {
        return list(new LambdaQueryWrapper<TssClassInfo>()
                .select(TssClassInfo::getId, TssClassInfo::getName, TssClassInfo::getPlace, TssClassInfo::getCapacity)
                .eq(TssClassInfo::getStatus, 0)
                .in(TssClassInfo::getPlace, place)
                .orderByAsc(TssClassInfo::getName));
    }

    public List<TssClassInfo> getUnOrderedClass(List<String> orderedClassIds, Integer place, Integer exType) {
        return list(new LambdaQueryWrapper<TssClassInfo>()
                .select(TssClassInfo::getId, TssClassInfo::getName, TssClassInfo::getPlace,
                        TssClassInfo::getCapacity, TssClassInfo::getType)
                .eq(TssClassInfo::getStatus, 0)
                .eq(TssClassInfo::getPlace, place)
                .eq(TssClassInfo::getType, exType)
                .notIn(TssClassInfo::getId, orderedClassIds)
                .orderByAsc(TssClassInfo::getName));
    }

    @Override
    public int getCapacityById(String classId) {

        return this.getBaseMapper().getCapacityById(classId);
    }
}
