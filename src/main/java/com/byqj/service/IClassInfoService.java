package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssClassInfo;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */
public interface IClassInfoService extends IService<TssClassInfo> {
    List<TssClassInfo> selectByPlace(List<Integer> place);

    List<TssClassInfo> getUnOrderedClass(List<String> orderedClassIds, Integer place, Integer exType);

    int getCapacityById(String classId);
}
