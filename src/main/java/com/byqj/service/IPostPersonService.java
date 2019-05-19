package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssPostPerson;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */
public interface IPostPersonService extends IService<TssPostPerson> {
    List<TssPostPerson> selectByExamPlaceIds(List<String> examPlaceIds);

    boolean delByTepIds(List<String> delIds);

    boolean delByTepIdsInTemp(List<String> readyDels);
}
